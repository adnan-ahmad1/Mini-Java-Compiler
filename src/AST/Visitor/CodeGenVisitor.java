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
    private int counter;
    private SemanticTable sm;
    private Map<String, List<String>> vTable;
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
        for (String c : sm.getClasses().keySet()) {
            if (vTable.containsKey(c)) {
                continue;
            }
            helper(c);
        }

    }

    private void helper(String c) {
        if (sm.getClass(c).getSuperClassName() == null) {
            vTable.put(c, new ArrayList<>());
        } else if (vTable.containsKey(sm.getClass(c).getSuperClassName())) {
            vTable.put(c, new ArrayList<>(vTable.get(sm.getClass(c).getSuperClassName())));
        } else {
            helper(sm.getClass(c).getSuperClassName());
            vTable.put(c, new ArrayList<>(vTable.get(sm.getClass(c).getSuperClassName())));
        }
        for (String m : sm.getClass(c).getMethodNames()) {
            boolean found = false;
            for (String vName: vTable.get(c)) {
                //System.out.println(Arrays.toString(vName.split("\\$")));
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

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);

        for ( int i = 0; i < n.cl.size(); i++ ) {
            n.cl.get(i).accept(this);
        }
        try {
            writeData();
            gen.finish();
        } catch(java.io.IOException e) {
        }

    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {

        try {
            gen.genLabel("_asm_main");
            gen.prologue();
            vTable.remove(n.i1.s);
        } catch(java.io.IOException e) {
        }

        n.s.accept(this);

        try {
            gen.epilogue();
        } catch(java.io.IOException e) {
        }
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        sm.goIntoClass(n.i.s);
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
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

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
        System.out.print(";");
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        try {
            sm.goIntoMethod(n.i.s);
            String methodH = sm.getCurrClassTable().getName() + "$" + n.i.s;
            gen.gen(methodH + ":");

            gen.prologue();
            for (int i = 0; i < n.fl.size();i++) {
                localVarOffset.put(n.fl.get(i).i.s, i);
            }
            for (int i = 0; i < n.vl.size(); i++) {
                localVarOffset.put(n.vl.get(i).i.s, i+n.fl.size());
            }
            if (localVarOffset.size()%2 == 1) {
                gen.gen("subq 8,%rsp");
                counter += 1;
            }
            gen.gen("subq " + (localVarOffset.size()*8) + ",%rsp");
            counter += localVarOffset.size();
            int j = 0;
            if (localVarOffset.size()%2 == 1) {
                gen.genbin("movq", "0xBADBADBADBADBADB", (localVarOffset.size()*(-8))+"(%rbp)");
                j = 1;
            }
            for(; j < n.fl.size();j++) {
                String assemblyCommand = "movq ";
                assemblyCommand += registers[j+1] + ",";
                assemblyCommand += ((j+1)*(-8));
                assemblyCommand += "(%rbp)";
                gen.gen(assemblyCommand);
            }
            for (int i = 0; i < n.sl.size(); i++) {
                n.sl.get(i).accept(this);
                gen.gen("");
            }
            n.e.accept(this);
            gen.epilogue();
            counter -= localVarOffset.size();
            localVarOffset.clear();
        } catch (Exception e) {

        }
        // higheraddress ->
    }

    // Type t;
    // Identifier i;
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

    // String s;
    public void visit(IdentifierType n) {
        System.out.print(n.s);
    }

    // StatementList sl;
    public void visit(Block n) {
        System.out.println("{ ");
        for ( int i = 0; i < n.sl.size(); i++ ) {
            System.out.print("      ");
            n.sl.get(i).accept(this);
            System.out.println();
        }
        System.out.print("    } ");
    }

    // Exp e;
    // Statement s1,s2;
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

    // Exp e;
    // Statement s;
    public void visit(While n) {
        System.out.print("while (");
        n.e.accept(this);
        System.out.print(") ");
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {


        n.e.accept(this);

        try {
            gen.genbin("    movq", "%rax", "%rdi");
            gen.gen("    call _put");
            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        System.out.print("[");
        n.e1.accept(this);
        System.out.print("] = ");
        n.e2.accept(this);
        System.out.print(";");
    }

    // Exp e1,e2;
    public void visit(And n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" && ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" < ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("    pushq %rax");
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

    // Exp e1,e2;
    public void visit(Minus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("    pushq %rax");
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

    // Exp e1,e2;
    public void visit(Times n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("    pushq %rax");
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

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        System.out.print("[");
        n.e2.accept(this);
        System.out.print("]");
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.e.accept(this);
        System.out.print(".length");
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        try {
            for (int i = 0; i < n.el.size(); i++) {
                if (counter%2 == 1) {
                    gen.pushDummy();
                }
                n.el.get(i).accept(this);
                if (counter%2 == 1) {
                    gen.popDummy();
                    counter--;
                }
                gen.gen("pushq %rax");
                counter++;
            }
            for (int i = n.el.size()-1; i >= 0; i--) {
                gen.gen("popq " + registers[i+1]);
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
            n.e.accept(this);
            int methodOffset = (vTable.get(n.e.type.toString())
                    .indexOf(n.e.type.toString() + "$" + n.i.toString()) * 8) + 8;
            gen.genbin("    movq", "%rax", "%rdi");
            gen.genbin("    movq", "0(%rdi)", "%rax");
            gen.gen("    call *" + methodOffset + "(%rax)");
            gen.gen("");
        } catch(java.io.IOException e) {
        }
    }

    // int i;
    public void visit(IntegerLiteral n) {

        try {
            gen.genbin("    movq", "$"+n.i, "%rax");
        } catch(java.io.IOException e) {
        }
    }

    public void visit(True n) {
        System.out.print("true");
    }

    public void visit(False n) {
        System.out.print("false");
    }

    // String s;
    public void visit(IdentifierExp n) {
        n.type = sm.getCurrMethodTable().getVarType(n.s);
        //System.out.print(n.s);
    }

    public void visit(This n) {
        System.out.print("this");
    }

    // Exp e;
    public void visit(NewArray n) {
        System.out.print("new int [");
        n.e.accept(this);
        System.out.print("]");
    }

    // Identifier i;
    public void visit(NewObject n) {
        try {
            String c = n.i.toString();
            int nBytesNeeded = sm.getClass(c).getOffset();
            gen.gen("    movq $" + (nBytesNeeded + 8) + ",%rdi");
            gen.gen("    call _mjcalloc");
            gen.gen("    leaq " + c + "$$(%rip),%rdx");
            gen.gen("    movq %rdx,0(%rax)");
            gen.gen("");
        } catch(Exception e) {

        }

        n.type = sm.getClass(n.i.toString());
    }

    // Exp e;
    public void visit(Not n) {
        System.out.print("!");
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
        System.out.print(n.s);
    }

}
