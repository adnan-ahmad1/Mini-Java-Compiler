package AST.Visitor;

import AST.*;
import CodeGen.Gen;
import Semantics.ClassSemanticTable;
import Semantics.SemanticTable;
import java.util.*;

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

    public CodeGenVisitor(SemanticTable sm) {
        counter = 0;
        controlFlowCounter = 0;
        gen = new Gen();
        this.sm  = sm;
        vTable = new HashMap<>();
        makeVTableInfo();
        fieldOffsets = new HashMap<>();
        calculateFieldOffsets();
        localVarOffset = new HashMap<>();
    }

    public void calculateFieldOffsets() {

        // go through variables of all classes and calculate their offsets
        for (String c : sm.getClasses().keySet()) {
            List<String> variables = new ArrayList<>();
            ClassSemanticTable cst = sm.getClass(c);

            calculateFieldOffsetsHelper(cst, variables);

            fieldOffsets.put(c, variables);
        }
    }

    public void calculateFieldOffsetsHelper(ClassSemanticTable cst, List<String> variables) {

        // calculate super class offsets first
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

        // write vtable at end
        writeData();
    }

    public void visit(MainClass n) {
        sm.goIntoClass(n.i1.s);
        sm.goIntoMethod("main");

        // visit main class and generate label
        gen.genLabel("asm_main");
        gen.prologue();
        vTable.remove(n.i1.s);

        // execute statements in main class
        n.s.accept(this);

        gen.epilogue();
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

        // generate unique id for label
        controlFlowCounter++;
        int labelID = controlFlowCounter;

        n.e.accept(this);

        // if 0 then it is the else branch of if statement
        gen.gen("cmpq $0,%rax \t\t # If statement");
        gen.gen("je " + sm.getCurrMethodTable().getName() + "_else_" + labelID);

        // not 0 so execute first statement
        n.s1.accept(this);

        // jump to done when first statement is finished
        gen.gen("jmp " + sm.getCurrMethodTable().getName() + "_done_" + labelID);


        // go to else and execute second statement
        gen.genLabel(sm.getCurrMethodTable().getName() + "_else_" + labelID);
        n.s2.accept(this);

        // in done, do nothing
        gen.genLabel(sm.getCurrMethodTable().getName() + "_done_" + labelID);
    }

    public void visit(While n) {
        controlFlowCounter++;
        int labelID = controlFlowCounter;

        // jmp to condition initially
        gen.gen("jmp " + sm.getCurrMethodTable().getName() + "_while_" + labelID + " \t\t # While statement");

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
    }

    public void visit(Print n) {

        // process expression inside print which is stored in %rax
        n.e.accept(this);


        // move %rax into %rdi since put call uses %rdi
        gen.genbin("movq", "%rax", "%rdi \t\t # Print");
        gen.gen("call put");
        gen.gen("");
    }

    public void visit(Assign n) {

        // process expression which will be stored in %rax
        n.e.accept(this);

        // calculate offset of identifier and move into offset from %rbp
        int offsetFromRbp;
        if (localVarOffset.containsKey(n.i.s)) {

            offsetFromRbp = localVarOffset.get(n.i.s) * 8;
            gen.genbin("movq", "%rax", -offsetFromRbp + "(%rbp) \t\t # Assign to local var");

        } else {

            offsetFromRbp = -1;
            List<String> fields = fieldOffsets.get(sm.getCurrClassTable().getName());
            for (int i = fields.size() - 1; i >= 0; i--) {
                if (fields.get(i).equals(n.i.s)) {
                    offsetFromRbp = (i * 8) + 8;
                    break;
                }
            }

            gen.gen("pushq %rax");
            gen.genbin("movq" , "-8(%rbp)", "%rax");
            gen.gen("popq %rdx");
            gen.genbin("movq", "%rdx", offsetFromRbp + "(%rax) \t\t # Assign to field");
        }
    }

    public void visit(ArrayAssign n) {
        n.e1.accept(this);

        gen.gen("pushq %rax");

        n.e2.accept(this);

        // calculate offset of identifier and move into offset from %rbp
        int offsetFromRbp;
        if (localVarOffset.containsKey(n.i.s)) {

            offsetFromRbp = localVarOffset.get(n.i.s) * 8;

            gen.gen("popq %rdx");
            gen.genbin("movq", -offsetFromRbp + "(%rbp)", "%rcx \t\t # Array Assign local var");

            // check array out of bounds: index >= length
            gen.gen("cmpq (%rcx),%rdx");
            gen.gen("jge runtime_error_exit");

            // check array out of bounds: index < 0
            gen.gen("cmpq $0,%rdx");
            gen.gen("jl runtime_error_exit");

            // calculate byte offset
            gen.genbin("imulq", "$8", "%rdx");
            gen.genbin("addq", "%rdx", "%rcx");
            gen.genbin("addq", "$8", "%rcx");
            gen.genbin("movq", "%rax", "(%rcx)");
        } else {
            offsetFromRbp = -1;
            List<String> fields = fieldOffsets.get(sm.getCurrClassTable().getName());
            for (int i = fields.size() - 1; i >= 0; i--) {
                if (fields.get(i).equals(n.i.s)) {
                    offsetFromRbp = (i * 8) + 8;
                    break;
                }
            }

            // push expression 2 on stack
            gen.gen("pushq %rax");

            // put array from fields into %rax
            gen.genbin("movq" , "-8(%rbp)", "%rax");
            gen.genbin("movq",offsetFromRbp + "(%rax)", "%rax");

            gen.gen("popq %rdx"); // exp 2
            gen.gen("popq %rcx"); // exp 1

            // check array out of bounds: index >= length
            gen.gen("cmpq (%rax),%rcx");
            gen.gen("jge runtime_error_exit");

            // check array out of bounds: index < 0
            gen.gen("cmpq $0,%rcx");
            gen.gen("jl runtime_error_exit");

            // get bytes to check from array
            gen.genbin("imulq", "$8", "%rcx");
            gen.genbin("addq", "%rcx", "%rax");
            gen.genbin("addq", "$8", "%rax");
            gen.genbin("movq", "%rdx", "(%rax)");
        }
    }

    public void visit(And n) {

        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        gen.gen("pushq %rax \t\t # Plus");

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and add both
        gen.gen("popq %rdx");
        gen.genbin("and", "%rdx", "%rax");
        gen.gen("");
    }

    public void visit(LessThan n) {
        // process expression 1
        n.e1.accept(this);

        gen.gen("pushq %rax");

        // process expression 2
        n.e2.accept(this);
        gen.gen("popq %rdx");

        // compare and set bit
        gen.gen("cmpq %rdx, %rax \t\t # Less Than");
        gen.gen("setg %al");
        gen.gen("movzbl %al,%eax");
    }

    public void visit(Plus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        gen.gen("pushq %rax \t\t # Plus");

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and add both
        gen.gen("popq %rdx");
        gen.genbin("addq", "%rdx", "%rax");
        gen.gen("");
    }

    public void visit(Minus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        gen.gen("pushq %rax \t\t # Minus");

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and subtract both
        gen.gen("popq %rdx");
        gen.genbin("subq", "%rax", "%rdx");
        gen.genbin("movq", "%rdx", "%rax");
        gen.gen("");
    }

    public void visit(Times n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        gen.gen("pushq %rax \t\t # Times");

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and multiply both
        gen.gen("popq %rdx");
        gen.genbin("imulq", "%rdx", "%rax");
        gen.gen("");
    }

    public void visit(ArrayLookup n) {
        n.e1.accept(this);

        gen.gen("pushq %rax");

        n.e2.accept(this);

        gen.gen("popq %rdx");

        // check array out of bounds: index >= length
        gen.gen("cmpq (%rdx),%rax");
        gen.gen("jge runtime_error_exit");

        // check array out of bounds: index < 0
        gen.gen("cmpq $0,%rax");
        gen.gen("jl runtime_error_exit");

        // add byte size to index and move array in %rdx to that location
        // then get value from that location
        gen.genbin("imulq", "$8", "%rax \t\t # Array Lookup");
        gen.genbin("addq", "%rax", "%rdx");
        gen.genbin("addq", "$8", "%rdx");
        gen.genbin("movq", "(%rdx)", "%rax");
        gen.gen("");
    }

    public void visit(ArrayLength n) {

        // process expression
        n.e.accept(this);

        // get length stored in first position
        gen.genbin("movq", "0(%rax)", "%rax \t\t # Array Length");
        gen.gen("");
    }

    public void visit(Call n) {
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
    }

    public void visit(IntegerLiteral n) {
        // move number into %rax
        gen.genbin("movq", "$"+n.i, "%rax \t\t # Integer Literal");
    }

    public void visit(True n) {
        // move true into %rax
        gen.genbin("movq", "$1", "%rax \t\t # Boolean true");
    }

    public void visit(False n) {
        // move false into %rax
        gen.genbin("movq", "$0", "%rax \t\t # Boolean false");
    }

    public void visit(IdentifierExp n) {

        // check if its in the local var offset table of method.
        // If it isn't, then it must be an instance variable of the class
        int offsetFromRbp;

        if (localVarOffset.containsKey(n.s)) {
            offsetFromRbp = localVarOffset.get(n.s) * 8;

            gen.genbin("movq", -offsetFromRbp + "(%rbp)", "%rax");
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

            gen.genbin("movq", "-8(%rbp)", "%rax");
            gen.genbin("movq", offsetFromRbp + "(%rax)", "%rax");
        }

    }

    public void visit(This n) {

        // this pointer of object calling method should be stored in %rbp
        gen.genbin("movq", "-8(%rbp)", "%rax \t\t # This");
    }

    public void visit(NewArray n) {
        n.e.accept(this);

        // check array out of bounds: index < 0
        gen.gen("cmpq $0,%rax");
        gen.gen("jl runtime_error_exit");

        // add size + 1 for length spot
        gen.genbin("addq", "$1", "%rax");
        gen.genbin("imulq", "$8", "%rax");

        // move 'bytes needed' for array to %rdi
        gen.genbin("movq", "%rax", "%rdi \t\t # New array declaration");
        gen.gen("call mjcalloc \t\t # Allocate space and return pointer in %rax");
        gen.gen("pushq %rax");

        // accept expression again to get length numbers
        counter++;
        n.e.accept(this);
        gen.gen("popq %rdx");
        counter--;

        // move size to beginning of array
        gen.genbin("movq", "%rax", "0(%rdx)");
        gen.genbin("movq", "%rdx", "%rax");
        gen.gen("");
    }

    public void visit(NewObject n) {
        String c = n.i.toString();

        // calculate bytes needed for class
        //int nBytesNeeded = sm.getClass(c).getOffset();
        int nBytesNeeded = (fieldOffsets.get(c).size() * 8) + 8;

        // load vtable at 0 offset of malloced bytes
        gen.gen("movq $" + nBytesNeeded + ",%rdi \t\t # New object declaration");
        gen.gen("call mjcalloc \t\t # Allocate space and return pointer in %rax");
        gen.gen("leaq " + c + "$$(%rip),%rdx \t\t # Load class vtable into %rdx");
        gen.gen("movq %rdx,0(%rax) \t\t # Load vtable at the beginning of %rax");
        gen.gen("");
    }

    public void visit(Not n) {
        n.e.accept(this);

        // just xor whatever bit is saved in %rax
        gen.genbin("xor", "$1", "%rax \t\t # Not");
        gen.gen("");
    }

    public void visit(Identifier n) {
    }
}
