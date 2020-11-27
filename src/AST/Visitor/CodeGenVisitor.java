package AST.Visitor;

import AST.*;
import CodeGen.Gen;
import Semantics.SemanticTable;
import java.util.*;
import java.io.IOException;


// java -cp build/classes:lib/java-cup-11b.jar MiniJava filename.java

public class CodeGenVisitor implements Visitor {
    private static final int VTABLE_OFFSET = 8;
    private static final String[] registers = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};
    private Gen gen;

    // indicates how much has been pushed onto the stack
    private int counter;
    private SemanticTable sm;

    // mapping from class name to list of unique methods
    private Map<String, List<String>> vTable;

    // mapping from variable name to offset in stack
    Map<String, Integer> localVarOffset;

    // one -> two -> three (super class)
    // one vtable.put(one, [plus, minus, times, divide])
    // two vtabe.put(two, [three$$plus, three$$minus]  plus, times
    // three vtable.put(three, [three$$plus, three$$minus])
    public CodeGenVisitor(SemanticTable sm) throws IOException {
        counter = 0;
        gen = new Gen("src/runtime/asmOutput.s");
        this.sm  = sm;
        vTable = new HashMap<>();
        makeVTableInfo();
        localVarOffset = new HashMap<>();

    }

    private void makeVTableInfo() {

        // go through semantic table classes and create a vtable for each class
        for (String c : sm.getClasses().keySet()) {
            if (vTable.containsKey(c)) {
                continue;
            }
            helper(c);
        }

    }

    private void helper(String c) {

        // first case: this is either a stand alone class or a superclass, so add methods to vtable
        // second case: class has a super class and vtable contains methods of super class
        // third case: class has a super class but the vtable doesn't have methods for superclass yet
        if (sm.getClass(c).getSuperClassName() == null) {
            vTable.put(c, new ArrayList<>());
        } else if (vTable.containsKey(sm.getClass(c).getSuperClassName())) {
            vTable.put(c, new ArrayList<>(vTable.get(sm.getClass(c).getSuperClassName())));
        } else {

            // add super class methods to vtable
            helper(sm.getClass(c).getSuperClassName());

            // insert super classes methods
            vTable.put(c, new ArrayList<>(vTable.get(sm.getClass(c).getSuperClassName())));
        }

        //go through class and add methods
        for (String m : sm.getClass(c).getMethodNames()) {
            boolean found = false;
            for (String vName: vTable.get(c)) {
                if ((vName.split("\\$")[1]).equals(m)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                vTable.get(c).add(c + "$" + m);
            }
        }
    }

    // write vtable
    private void writeData() {
        try {
            gen.gen(".data");
            for (String key : vTable.keySet()) {
                gen.gen(key + "$$:");

                gen.gen("    .quad 0");

                List<String> methods = vTable.get(key);
                for (String method : methods) {
                    gen.gen("    .quad " + method);
                }
            }
        } catch(java.io.IOException e) {
        }
    }

    // Display added for toy example language.  Not used in regular MiniJava
    public void visit(Display n) {
    }

    public void visit(Program n) {

        // go through main class
        n.m.accept(this);

        // go through all other classes
        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.get(i).accept(this);
        }

        try {

            // write vtable at and close writer
            writeData();
            gen.finish();
        } catch(java.io.IOException e) {
        }

    }

    public void visit(MainClass n) {

        // visit main class and generate label
        try {
            gen.genLabel("_asm_main");
            gen.prologue();
            vTable.remove(n.i1.s);
        } catch(java.io.IOException e) {
        }

        // execute statements in main class
        n.s.accept(this);

        try {
            gen.epilogue();
        } catch(java.io.IOException e) {
        }
    }

    public void visit(ClassDeclSimple n) {
        sm.goIntoClass(n.i.s);
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
    }

    public void visit(ClassDeclExtends n) {
        System.out.print("class ");
        n.i.accept(this);
        System.out.println(" extends ");
        n.j.accept(this);
        System.out.println(" { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print("  ");
            n.vl.get(i).accept(this);
            if ( i+1 < n.vl.size() ) { System.out.println(); }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            System.out.println();
            n.ml.get(i).accept(this);
        }
        System.out.println();
        System.out.println("}");
    }

    public void visit(VarDecl n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
        System.out.print(";");
    }

    public void visit(MethodDecl n) {
        try {
            sm.goIntoMethod(n.i.s);

            // generate label for method and create prologue
            String methodH = sm.getCurrClassTable().getName() + "$" + n.i.s;
            gen.gen(methodH + ":");
            gen.prologue();

            // go through parameters and input offsets
            for (int i = 0; i < n.fl.size();i++) {
                localVarOffset.put(n.fl.get(i).i.s, i);
            }

            // go through local variables and input offsets
            for (int i = 0; i < n.vl.size(); i++) {
                localVarOffset.put(n.vl.get(i).i.s, i+n.fl.size());
            }

            if (localVarOffset.size()%2 == 1) {
                gen.gen("    subq 8,%rsp");
                counter += 1;
            }

            // subtract space from stack for variables and add to counter
            gen.gen("    subq " + (localVarOffset.size()*8) + ",%rsp \t\t # Subtract space for variables to push on stack");
            counter += localVarOffset.size();

            int j = 0;
            if (localVarOffset.size()%2 == 1) {
                gen.genbin("    movq", "%rax", (localVarOffset.size()*(-8))+"(%rbp)");
                j = 1;
            }

            // go through parameters and nove register arg values to stack
            for(; j < n.fl.size();j++) {
                String assemblyCommand = "    movq ";
                assemblyCommand += registers[j+1] + ",";
                assemblyCommand += ((j+1)*(-8));
                assemblyCommand += "(%rbp) \t\t # Move variable onto stack";
                gen.gen(assemblyCommand);
            }

            // execute statements within the method
            // only on assigns do you set value to local variable offsets
            for (int i = 0; i < n.sl.size(); i++) {
                n.sl.get(i).accept(this);
            }

            // process return expression which is stored in %rax
            n.e.accept(this);

            // remove space from stack
            if (localVarOffset.size()%2 == 1) {
                gen.gen("    addq " + (localVarOffset.size()+1)*8 + ",%rsp \t\t # Remove space from top of stack frame");
                counter--;
            } else {
                gen.gen("    addq " + (localVarOffset.size())*8 + ",%rsp \t\t # Remove space from top of stack frame");
            }

            gen.gen("");
            gen.epilogue();
            counter -= localVarOffset.size();

            // TODO: Still needed?
            localVarOffset.clear();
        } catch (Exception e) {

        }
    }

    public void visit(Formal n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
        System.out.print("int []");
    }

    public void visit(BooleanType n) {
        System.out.print("boolean");
    }

    public void visit(IntegerType n) {
        //System.out.print("int");

    }

    public void visit(IdentifierType n) {
        System.out.print(n.s);
    }

    public void visit(Block n) {
        System.out.println("{ ");
        for ( int i = 0; i < n.sl.size(); i++ ) {
            System.out.print("      ");
            n.sl.get(i).accept(this);
            System.out.println();
        }
        System.out.print("    } ");
    }

    public void visit(If n) {
        System.out.print("if (");
        n.e.accept(this);
        System.out.println(") ");
        System.out.print("    ");
        n.s1.accept(this);
        System.out.println();
        System.out.print("    else ");
        n.s2.accept(this);
    }

    public void visit(While n) {
        System.out.print("while (");
        n.e.accept(this);
        System.out.print(") ");
        n.s.accept(this);
    }

    public void visit(Print n) {

        // process expression inside print which is stored in %rax
        n.e.accept(this);

        try {

            // move %rax into %rdi since put call uses %rdi
            gen.genbin("    movq", "%rax", "%rdi \t\t # Print");
            gen.gen("    call _put");
            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(Assign n) {
        n.e.accept(this);
    }

    public void visit(ArrayAssign n) {
        n.i.accept(this);
        System.out.print("[");
        n.e1.accept(this);
        System.out.print("] = ");
        n.e2.accept(this);
        System.out.print(";");
    }

    public void visit(And n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" && ");
        n.e2.accept(this);
        System.out.print(")");
    }

    public void visit(LessThan n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" < ");
        n.e2.accept(this);
        System.out.print(")");
    }

    public void visit(Plus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("    pushq %rax \t\t # Plus");
        } catch(java.io.IOException e) {
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and add both
        try {
            gen.gen("    popq %rdx");
            gen.genbin("    addq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(Minus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("    pushq %rax \t\t # Minus");
        } catch(java.io.IOException e) {
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and subtract both
        try {
            gen.gen("    popq %rdx");
            gen.genbin("    subq", "%rax", "%rdx");
            gen.genbin("    movq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(Times n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("    pushq %rax \t\t # Times");
        } catch(java.io.IOException e) {
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and multiply both
        try {
            gen.gen("    popq %rdx");
            gen.genbin("    imulq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        System.out.print("[");
        n.e2.accept(this);
        System.out.print("]");
    }

    public void visit(ArrayLength n) {
        n.e.accept(this);
        System.out.print(".length");
    }

    public void visit(Call n) {
        try {

            boolean flag = false;

            // go through expressions in args and process them
            for (int i = 0; i < n.el.size(); i++) {

                // if the stack is misaligned, then align by adding dummy variable (in case of nested call)
                if (counter%2 == 1) {
                    gen.pushDummy();
                    counter++;
                    flag = true;
                }

                // process expression in arg
                n.el.get(i).accept(this);

                // if dummy value was added to align stack, then pop dummy off
                if (flag) {
                    gen.popDummy();
                    counter--;
                    flag = false;
                }

                // push processed expression of arg onto stack so registers do not get clobbered
                gen.gen("    pushq %rax \t\t # Evaluate args and push on stack");
                counter++;
            }

            // pop args off of stack and store in registers for method call
            for (int i = n.el.size()-1; i >= 0; i--) {
                gen.gen("    popq " + registers[i+1] + " \t\t # Pop from stack into arg registers");
            }
            // obj.call(exp1, obj.call2(1), exp3)
            // finds vtable and calls function
            /*
                fun(int n, int m, int g, int q) {
                    new Two().fun(1, 2, fun, fun(1,2,3, 4))
                }
                n -> m -> g -> q -> 1 -> 2- > vakfhaal
                visitor(Call n)
                    rax = exp2
                    ex1 -> exp2 - >
            */

            // process variable making the call which will be stored in %rax
            n.e.accept(this);

            // calculate offset from class vtable
            int methodOffset = (vTable.get(n.e.type.toString())
                    .indexOf(n.e.type.toString() + "$" + n.i.toString()) * 8) + 8;

            // load variable's vtable
            gen.genbin("    movq", "%rax", "%rdi \t\t # Load variable's vtable");
            gen.genbin("    movq", "0(%rdi)", "%rax");

            // execute call from certain offset in vtable
            gen.gen("    call *" + methodOffset + "(%rax) \t\t # Call variable's method");

            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(IntegerLiteral n) {

        try {
            // move number into %rax
            gen.genbin("    movq", "$"+n.i, "%rax \t\t # Integer Literal");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(True n) {
        System.out.print("true");
    }

    public void visit(False n) {
        System.out.print("false");
    }

    public void visit(IdentifierExp n) {
        n.type = sm.getCurrMethodTable().getVarType(n.s);
        //System.out.print(n.s);
    }

    public void visit(This n) {
        System.out.print("this");
    }

    public void visit(NewArray n) {
        System.out.print("new int [");
        n.e.accept(this);
        System.out.print("]");
    }

    public void visit(NewObject n) {
        try {
            String c = n.i.toString();

            // calculate bytes needed for class
            int nBytesNeeded = sm.getClass(c).getOffset();

            // load vtable at 0 offset of malloced bytes
            gen.gen("    movq $" + (nBytesNeeded + 8) + ",%rdi \t\t # New object declaration");
            gen.gen("    call _mjcalloc \t\t # Allocate space and return pointer in %rax");
            gen.gen("    leaq " + c + "$$(%rip),%rdx \t\t # Load class vtable into %rdx");
            gen.gen("    movq %rdx,0(%rax) \t\t # Load vtable at the beginning of %rax");
            gen.gen("");
        } catch(Exception e) {

        }

        n.type = sm.getClass(n.i.toString());
    }

    public void visit(Not n) {
        System.out.print("!");
        n.e.accept(this);
    }

    public void visit(Identifier n) {
        System.out.print(n.s);
    }

}
