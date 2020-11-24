package AST.Visitor;

import AST.*;
import CodeGen.Gen;
import Semantics.SemanticTable;
import java.util.*;
import java.io.IOException;

public class CodeGenVisitor implements Visitor {
    private static final int VTABLE_OFFSET = 8;
    private Gen gen;
    private int counter;
    private SemanticTable sm;
    private Map<String, List<String>> vTable;

    // one -> two -> three (super class)
    // one vtable.put(one, [plus, minus, times, divide])
    // two vtabe.put(two, [three$$plus, three$$minus]  plus, times
    // three vtable.put(three, [three$$plus, three$$minus])
    public CodeGenVisitor(SemanticTable sm) throws IOException {
        counter = 0;
        gen = new Gen("src/runtime/asmOutput.s");
        this.sm  = sm;
        makeVTableInfo();
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
                if (vName.split("$")[1].equals(m)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                vTable.get(c).add(c + "$" + m);
            }
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
            for (int i = 0; i < n.sl.size(); i++) {
                n.sl.get(i).accept(this);
            }
            n.e.accept(this);
        } catch (Exception e) {

        }

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
        System.out.print("int");
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
            gen.genbin("movq", "%rax", "%rdi");
            gen.gen("call _put");
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
            gen.gen("pushq %rax");
        } catch(java.io.IOException e) {
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and add both
        try {
            gen.gen("popq %rdx");
            gen.genbin("addq", "%rdx", "%rax");
        } catch(java.io.IOException e) {
        }
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("pushq %rax");
        } catch(java.io.IOException e) {
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and subtract both
        try {
            gen.gen("popq %rdx");
            gen.genbin("subq", "%rax", "%rdx");
            gen.genbin("movq", "%rdx", "%rax");
        } catch(java.io.IOException e) {
        }
    }

    // Exp e1,e2;
    public void visit(Times n) {
        // visit first expression and put result in %rax
        n.e1.accept(this);

        // push expression 1 onto stack
        try {
            gen.gen("pushq %rax");
        } catch(java.io.IOException e) {
        }

        // visit second expression and put result in %rax
        n.e2.accept(this);

        // pop expression 1 into %rdx and multiply both
        try {
            gen.gen("popq %rdx");
            gen.genbin("imulq", "%rdx", "%rax");
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
        n.e.accept(this);
        System.out.print(".");
        n.i.accept(this);
        System.out.print("(");
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.get(i).accept(this);
            if ( i+1 < n.el.size() ) { System.out.print(", "); }
        }
        System.out.print(")");
    }

    // int i;
    public void visit(IntegerLiteral n) {

        try {
            gen.genbin("movq", "$"+n.i, "%rax");
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
        System.out.print(n.s);
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
            int offset = sm.getClass(c).getOffset();
            gen.gen("movq $" + (offset+8) + ",%rdi");
            gen.gen("call _mjcalloc");
            gen.gen("leaq " + c + "$$(%rip),");
            gen.gen("movq %rdx,0(%rax)");

        } catch(Exception e) {

        }
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
