package AST.Visitor;

import AST.*;
import CodeGen.Gen;
import Semantics.ClassSemanticTable;
import Semantics.SemanticTable;
import java.util.*;
import java.io.IOException;

// java -cp build/classes:lib/java-cup-11b.jar MiniJava filename.java

public class CodeGenVisitor implements Visitor {
    private static final String[] registers = {"%rdi", "%rsi", "%rdx", "%rcx", "%r8", "%r9"};
    private Gen gen;

    // indicates how much has been pushed onto the stack
    private int counter;
    private SemanticTable sm;
    private int controlFlowCounter;

    // mapping from class name to list of unique methods
    private Map<String, List<String>> vTable;

    // mapping from class name to list of variable names
    private Map<String, List<String>> fieldOffsets;

    // mapping from variable name to offset in stack
    Map<String, Integer> localVarOffset;

    public CodeGenVisitor(SemanticTable sm) throws IOException {
        counter = 0;
        controlFlowCounter = 0;
        gen = new Gen("src/runtime/asmOutput.s");
        this.sm  = sm;
        vTable = new HashMap<>();
        makeVTableInfo();
        fieldOffsets = new HashMap<>();
        calculateFieldOffsets();
        localVarOffset = new HashMap<>();
    }

    public void calculateFieldOffsets() {
        for (String c : sm.getClasses().keySet()) {
            List<String> variables = new ArrayList<>();
            ClassSemanticTable cst = sm.getClass(c);

            calculateFieldOffsetsHelper(cst, variables);

            fieldOffsets.put(c, variables);
        }
    }

    public void calculateFieldOffsetsHelper(ClassSemanticTable cst, List<String> variables) {
        if (cst.getSuperClass() != null) {
            calculateFieldOffsetsHelper(cst.getSuperClass(), variables);
        }

        for (String varName : cst.getVariableNames()) {
            variables.add(varName);
        }
    }

    private void makeVTableInfo() {

        // go through semantic table classes and create a vtable for each class
        for (String c : sm.getClasses().keySet()) {
            if (vTable.containsKey(c)) {
                continue;
            }
            makeVTableInfoHelper(c);
        }
    }

    private void makeVTableInfoHelper(String c) {

        // first case: this is either a stand alone class or a superclass, so add methods to vtable
        // second case: class has a super class and vtable contains methods of super class
        // third case: class has a super class but the vtable doesn't have methods for superclass yet
        if (sm.getClass(c).getSuperClassName() == null) {
            vTable.put(c, new ArrayList<>());
        } else if (vTable.containsKey(sm.getClass(c).getSuperClassName())) {
            vTable.put(c, new ArrayList<>(vTable.get(sm.getClass(c).getSuperClassName())));
        } else {

            // add super class methods to vtable
            makeVTableInfoHelper(sm.getClass(c).getSuperClassName());

            // insert super classes methods
            vTable.put(c, new ArrayList<>(vTable.get(sm.getClass(c).getSuperClassName())));
        }

        //go through class and add methods
        for (String m : sm.getClass(c).getMethodNames()) {
            boolean found = false;
            for (int i = 0; i < vTable.get(c).size(); i++) {
                String vName = vTable.get(c).get(i);
                if ((vName.split("\\$")[1]).equals(m)) {
                    vTable.get(c).remove(i);
                    vTable.get(c).add(i, c + "$" + m);
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

                if (sm.getClass(key).getSuperClass() != null) {
                    gen.gen(".quad " + sm.getClass(key).getSuperClassName() + "$$");
                } else {
                    gen.gen(".quad 0");
                }

                List<String> methods = vTable.get(key);
                for (String method : methods) {
                    gen.gen(".quad " + method);
                }
            }
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
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
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

    }

    public void visit(MainClass n) {
        sm.goIntoClass(n.i1.s);
        sm.goIntoMethod("main");

        // visit main class and generate label
        try {
            gen.genLabel("_asm_main");
            gen.prologue();
            vTable.remove(n.i1.s);
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        // execute statements in main class
        n.s.accept(this);

        try {
            gen.epilogue();
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(ClassDeclSimple n) {
        sm.goIntoClass(n.i.s);
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
    }

    public void visit(ClassDeclExtends n) {
        sm.goIntoClass(n.i.s);
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
        }
    }

    public void visit(VarDecl n) {
    }

    public void visit(MethodDecl n) {
        try {
            sm.goIntoMethod(n.i.s);

            // find method name
            String methodH = "";
            List<String> methods = vTable.get(sm.getCurrClassTable().getName());
            for (int i = 0; i < methods.size(); i++) {
                int dollarSign = methods.get(i).indexOf("$");
                if (methods.get(i).substring(dollarSign + 1).equals(n.i.s)) {
                    methodH = methods.get(i);
                    break;
                }
            }

            // generate label for method and create prologue
            gen.genLabel(methodH);
            gen.prologue();

            // go through parameters and input offsets
            for (int i = 0; i < n.fl.size();i++) {
                localVarOffset.put(n.fl.get(i).i.s, i + 2);
            }

            // go through local variables and input offsets
            for (int i = 0; i < n.vl.size(); i++) {
                localVarOffset.put(n.vl.get(i).i.s, ((i + 2) + n.fl.size()));
            }

            // if we have an even number of variables, then we push dummy variable ('this' is passed but not counted)
            if (localVarOffset.size()%2 == 0) {
                gen.gen("subq $8,%rsp");
                counter += 1;
            }

            // subtract space from stack for variables and add to counter
            gen.gen("subq " + "$" + ((localVarOffset.size() + 1) * 8) + ",%rsp \t\t # Subtract space for variables to push on stack");
            counter += (localVarOffset.size() + 1);

            // go through parameters and move register arg values to stack
            for(int j = 0; j <= n.fl.size();j++) {
                String assemblyCommand = "movq ";
                assemblyCommand += registers[j] + ",";
                assemblyCommand += ((j + 1) * (-8));
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

            // remove space for dummy val from counter
            if (localVarOffset.size() % 2 == 0) {
                counter--;
            }
            counter -= (localVarOffset.size() + 1);

            gen.gen("");
            gen.epilogue();

            localVarOffset.clear();
        } catch (Exception e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Formal n) {
    }

    public void visit(IntArrayType n) {
    }

    public void visit(BooleanType n) {
    }

    public void visit(IntegerType n) {

    }

    public void visit(IdentifierType n) {
    }

    public void visit(Block n) {

        // process list of statements
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
        }
    }

    public void visit(If n) {
        try {
            controlFlowCounter++;
            int labelID = controlFlowCounter;

            n.e.accept(this);
            gen.gen("cmpq $0,%rax");
            gen.gen("je " + sm.getCurrMethodTable().getName() + "_else_" + labelID);

            n.s1.accept(this);
            gen.gen("jmp " + sm.getCurrMethodTable().getName() + "_done_" + labelID);
            gen.genLabel(sm.getCurrMethodTable().getName() + "_else_" + labelID);
            n.s2.accept(this);
            gen.genLabel(sm.getCurrMethodTable().getName() + "_done_" + labelID);
        } catch (Exception e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

    }

    public void visit(While n) {
        try {
            controlFlowCounter++;
            int labelID = controlFlowCounter;

            // jmp to condition initially
            gen.gen("jmp " + sm.getCurrMethodTable().getName() + "_while_" + labelID);

            // generate label for statement in while
            gen.genLabel(sm.getCurrMethodTable().getName() + "_while_" + labelID);
            n.e.accept(this);
            gen.gen("cmpq $0,%rax");

            // if condition is false go to done
            gen.gen("je " + sm.getCurrMethodTable().getName() + "_while_done_" + labelID);

            // process statement inside
            n.s.accept(this);

            // jmp back to condition
            gen.gen("jmp " + sm.getCurrMethodTable().getName() + "_while_" + labelID);

            // done label
            gen.genLabel(sm.getCurrMethodTable().getName() + "_while_done_" + labelID);

        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Print n) {

        // process expression inside print which is stored in %rax
        n.e.accept(this);

        try {

            // move %rax into %rdi since put call uses %rdi
            gen.genbin("movq", "%rax", "%rdi \t\t # Print");
            gen.gen("call _put");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Assign n) {

        // process expression which will be stored in %rax
        n.e.accept(this);

        // calculate offset of identifier and move into offset from %rbp
        int offsetFromRbp;
        if (localVarOffset.containsKey(n.i.s)) {

            offsetFromRbp = localVarOffset.get(n.i.s) * 8;

            try {
                gen.genbin("movq", "%rax", -offsetFromRbp + "(%rbp)");
            } catch(java.io.IOException e) {
                System.err.println("Unexpected IO exception error: " + e.toString());
                System.exit(1);
            }
        } else {

            offsetFromRbp = -1;
            List<String> fields = fieldOffsets.get(sm.getCurrClassTable().getName());
            for (int i = fields.size() - 1; i >= 0; i--) {
                if (fields.get(i).equals(n.i.s)) {
                    offsetFromRbp = (i * 8) + 8;
                    break;
                }
            }

            try {
                gen.gen("pushq %rax");
                gen.genbin("movq" , "-8(%rbp)", "%rax");
                gen.gen("popq %rdx");
                gen.genbin("movq", "%rdx", offsetFromRbp + "(%rax)");
            } catch(java.io.IOException e) {
                System.err.println("Unexpected IO exception error: " + e.toString());
                System.exit(1);
            }
        }
    }

    public void visit(ArrayAssign n) {
        n.e1.accept(this);

        try {
            gen.gen("pushq %rax");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        n.e2.accept(this);

        // calculate offset of identifier and move into offset from %rbp
        int offsetFromRbp;
        if (localVarOffset.containsKey(n.i.s)) {

            offsetFromRbp = localVarOffset.get(n.i.s) * 8;

            try {
                gen.gen("popq %rdx");
                gen.genbin("movq", -offsetFromRbp + "(%rbp)", "%rcx");
                gen.genbin("imulq", "$8", "%rdx");
                gen.genbin("addq", "%rdx", "%rcx");
                gen.genbin("addq", "$8", "%rcx");
                gen.genbin("movq", "%rax", "(%rcx)");
            } catch(java.io.IOException e) {
                System.err.println("Unexpected IO exception error: " + e.toString());
                System.exit(1);
            }
        } else {
            offsetFromRbp = -1;
            List<String> fields = fieldOffsets.get(sm.getCurrClassTable().getName());
            for (int i = fields.size() - 1; i >= 0; i--) {
                if (fields.get(i).equals(n.i.s)) {
                    offsetFromRbp = (i * 8) + 8;
                    break;
                }
            }

            try {
                gen.gen("pushq %rax");
                gen.genbin("movq" , "-8(%rbp)", "%rax");
                gen.genbin("movq",offsetFromRbp + "(%rax)", "%rax");

                gen.gen("popq %rdx"); // exp 2
                gen.gen("popq %rcx"); // exp 1

                gen.genbin("imulq", "$8", "%rcx");
                gen.genbin("addq", "%rcx", "%rax");
                gen.genbin("addq", "$8", "%rax");

                gen.genbin("movq", "%rdx", "(%rax)");
            } catch(java.io.IOException e) {
                System.err.println("Unexpected IO exception error: " + e.toString());
                System.exit(1);
            }
        }
    }

    public void visit(And n) {

        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("pushq %rax \t\t # Plus");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and add both
        try {
            gen.gen("popq %rdx");
            gen.genbin("and", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

    }

    public void visit(LessThan n) {
        try {
            n.e1.accept(this);
            gen.gen("pushq %rax");
            n.e2.accept(this);
            gen.gen("popq %rdx");
            gen.gen("cmpq %rdx, %rax");
            gen.gen("setg %al");
            gen.gen("movzbl %al,%eax");
        } catch(Exception e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

    }

    public void visit(Plus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("pushq %rax \t\t # Plus");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and add both
        try {
            gen.gen("popq %rdx");
            gen.genbin("addq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Minus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("pushq %rax \t\t # Minus");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and subtract both
        try {
            gen.gen("popq %rdx");
            gen.genbin("subq", "%rax", "%rdx");
            gen.genbin("movq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Times n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("pushq %rax \t\t # Times");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and multiply both
        try {
            gen.gen("popq %rdx");
            gen.genbin("imulq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        try {
            gen.gen("pushq %rax");

        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

        n.e2.accept(this);

        try {
            gen.gen("popq %rdx");
            gen.genbin("imulq", "$8", "%rax");
            gen.genbin("addq", "%rax", "%rdx");
            gen.genbin("addq", "$8", "%rdx");
            gen.genbin("movq", "(%rdx)", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(ArrayLength n) {
        n.e.accept(this);

        try {
        gen.genbin("movq", "0(%rax)", "%rax");
        gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Call n) {
        try {

            boolean flag = false;

            // go through expressions in args and process them
            for (int i = 0; i < n.el.size(); i++) {

                // if the stack is misaligned, then align by adding dummy variable (in case of nested call)
                if (counter % 2 == 1) {
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
                gen.gen("pushq %rax \t\t # Evaluate args and push on stack");
                counter++;
            }

            // before evaluating expression which could make another method call,
            // push %rax if stack is not aligned
            if (counter % 2 == 1) {
                gen.pushDummy();
                counter++;
                flag = true;
            }

            // process variable making the call which will be stored in %rax
            n.e.accept(this);

            // check if dummy value was pushed
            if (flag) {
                gen.popDummy();
                counter--;
                flag = false;
            }

            // pop args off of stack and store in registers for method call
            for (int i = n.el.size()-1; i >= 0; i--) {
                gen.gen("popq " + registers[i+1] + " \t\t # Pop from stack into arg registers");
            }

            // calculate offset from class vtable
            int methodOffset = -1;
            List<String> methods = vTable.get(n.e.type.toString());
            for (int i = 0; i < methods.size(); i++) {
                int dollarSign = methods.get(i).indexOf("$");
                if (methods.get(i).substring(dollarSign + 1).equals(n.i.toString())) {
                    methodOffset = (i * 8) + 8;
                    break;
                }
            }

            // load variable's vtable
            gen.genbin("movq", "%rax", "%rdi \t\t # Load pointer of object making call in first arg register");
            gen.genbin("movq", "0(%rdi)", "%rax");

            // execute call from certain offset in vtable
            gen.gen("call *" + methodOffset + "(%rax) \t\t # Call variable's method");

            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(IntegerLiteral n) {

        try {
            // move number into %rax
            gen.genbin("movq", "$"+n.i, "%rax \t\t # Integer Literal");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(True n) {
        try {
            // move true into %rax
            gen.genbin("movq", "$1", "%rax \t\t # Boolean true");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(False n) {
        try {
            // move false into %rax
            gen.genbin("movq", "$0", "%rax \t\t # Boolean false");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(IdentifierExp n) {

        // check if its in the local var offset table of method.
        // If it isn't, then it must be an instance variable of the class
        int offsetFromRbp;

        if (localVarOffset.containsKey(n.s)) {
            offsetFromRbp = localVarOffset.get(n.s) * 8;

            try {
                gen.genbin("movq", -offsetFromRbp + "(%rbp)", "%rax");
            } catch(Exception e) {
                System.err.println("Unexpected IO exception error: " + e.toString());
                System.exit(1);
            }
        } else {
            //offsetFromRbp = (sm.getCurrClassTable().getVariableNames().indexOf(n.s) * 8) + 8;
            offsetFromRbp = -1;
            List<String> fields = fieldOffsets.get(sm.getCurrClassTable().getName());
            for (int i = fields.size() - 1; i >= 0; i--) {
                if (fields.get(i).equals(n.s)) {
                    offsetFromRbp = (i * 8) + 8;
                    break;
                }
            }

            try {
                gen.genbin("movq", "-8(%rbp)", "%rax");
                gen.genbin("movq", offsetFromRbp + "(%rax)", "%rax");
            } catch(java.io.IOException e) {
                System.err.println("Unexpected IO exception error: " + e.toString());
                System.exit(1);
            }
        }

    }

    public void visit(This n) {
        try {
            gen.genbin("movq", "-8(%rbp)", "%rax");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(NewArray n) {
        n.e.accept(this);

        try {
            gen.genbin("addq", "$1", "%rax");
            gen.genbin("imulq", "$8", "%rax");
            gen.genbin("movq", "%rax", "%rdi \t\t # New array declaration");
            gen.gen("call _mjcalloc \t\t # Allocate space and return pointer in %rax");
            gen.gen("pushq %rax");
            counter++;
            n.e.accept(this);
            gen.gen("popq %rdx");
            counter--;
            gen.genbin("movq", "%rax", "0(%rdx)");
            gen.genbin("movq", "%rdx", "%rax");
            gen.gen("");
        } catch(java.io.IOException e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(NewObject n) {
        try {
            String c = n.i.toString();

            // calculate bytes needed for class
            //int nBytesNeeded = sm.getClass(c).getOffset();
            int nBytesNeeded = (fieldOffsets.get(c).size() * 8) + 8;

            // load vtable at 0 offset of malloced bytes
            gen.gen("movq $" + nBytesNeeded + ",%rdi \t\t # New object declaration");
            gen.gen("call _mjcalloc \t\t # Allocate space and return pointer in %rax");
            gen.gen("leaq " + c + "$$(%rip),%rdx \t\t # Load class vtable into %rdx");
            gen.gen("movq %rdx,0(%rax) \t\t # Load vtable at the beginning of %rax");
            gen.gen("");
        } catch(Exception e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }

    }

    public void visit(Not n) {
        n.e.accept(this);

        try {
            gen.genbin("xor", "$1", "%rax");
            gen.gen("");
        } catch(Exception e) {
            System.err.println("Unexpected IO exception error: " + e.toString());
            System.exit(1);
        }
    }

    public void visit(Identifier n) {
    }
}
