package AST.Visitor;

import AST.*;

// Sample print visitor from MiniJava web site with small modifications for UW CSE.
// HP 10/11

public class AbstractTreeVisitor implements Visitor {
    private static StringBuilder indents = new StringBuilder();
    // Display added for toy example language.  Not used in regular MiniJava
    public void visit(Display n) {
        System.out.print("display ");
        n.e.accept(this);
        System.out.print(";");
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        System.out.println("Program");
        indents.append("  ");
        System.out.print(indents.toString());
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            System.out.println();
            System.out.print(indents.toString());
            n.cl.get(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        System.out.print("MainClass ");
        n.i1.accept(this);
        System.out.println(" (line " + n.line_number + ")");
        indents.append("  ");
        System.out.print(indents.toString());
        n.s.accept(this);
        indents.delete(indents.length()-2,indents.length());
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        System.out.print("Class ");
        n.i.accept(this);
        System.out.println(" (line "+n.line_number+")");
        // Variable declarations
        indents.append("  ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print(indents.toString());
            n.vl.get(i).accept(this);
            if ( i+1 < n.vl.size() ) { System.out.println(); }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            if(i!= 0 || n.vl.size() != 0) {
                System.out.println();
            }
            System.out.print(indents.toString());
            n.ml.get(i).accept(this);
        }
        indents.delete(indents.length()-2, indents.length());
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        System.out.print("Class ");
        n.i.accept(this);
        System.out.print(" extends ");
        n.j.accept(this);
        System.out.println( " (line "+n.line_number +")");
        indents.append("  ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print(indents.toString());
            n.vl.get(i).accept(this);
            if ( i+1 < n.vl.size() ) { System.out.println(); }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            if(i!= 0 || n.vl.size() != 0) {
                System.out.println();
            }
            System.out.print(indents.toString());
            n.ml.get(i).accept(this);
        }
        indents.delete(indents.length()-2, indents.length());
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        System.out.print("MethodDecl ");
        n.i.accept(this);
        System.out.println(" (line "+n.line_number+")");
        indents.append("  ");
        System.out.print(indents.toString()+"returns ");
        n.t.accept(this);
        System.out.println();
        System.out.print(indents.toString() + "parameters:");
        indents.append("  ");
        for ( int i = 0; i < n.fl.size(); i++ ) {
            System.out.println();
            System.out.print(indents.toString());
            n.fl.get(i).accept(this);
        }
        indents.delete(indents.length()-2, indents.length());
        System.out.println();
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print(indents.toString());
            n.vl.get(i).accept(this);
            System.out.println("");
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            System.out.print(indents.toString());
            n.sl.get(i).accept(this);
            if ( i < n.sl.size() ) { System.out.println(""); }
        }
        System.out.print(indents.toString());
        System.out.print("Return ");
        n.e.accept(this);
        System.out.print(" (line " + n.e.line_number + ")");
        indents.delete(indents.length()-2,indents.length());
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
        if (n.sl.size() == 0) {
            return;
        }

        n.sl.get(0).accept(this);
        for ( int i = 1; i < n.sl.size(); i++ ) {
            System.out.println();
            System.out.print(indents.toString());
            n.sl.get(i).accept(this);
        }
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        System.out.print("If ");
        n.e.accept(this);
        System.out.println(": (line "+n.line_number+")");
        indents.append("  ");
        System.out.print(indents.toString());
        n.s1.accept(this);
        System.out.println();
        indents.delete(indents.length()-2, indents.length());
        System.out.print(indents.toString());
        System.out.println("else:");
        indents.append("  ");
        System.out.print(indents.toString());
        n.s2.accept(this);
        indents.delete(indents.length()-2, indents.length());
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        System.out.print("While ");
        n.e.accept(this);
        System.out.println(": (line "+n.line_number+")");
        indents.append("  ");
        System.out.print(indents.toString());
        n.s.accept(this);
        indents.delete(indents.length()-2, indents.length());
    }

    // Exp e;
    public void visit(Print n) {
        System.out.println("Print (line "+n.line_number+")");
        indents.append("  ");
        System.out.print(indents.toString());
        n.e.accept(this);
        indents.delete(indents.length()-2, indents.length());
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.i.accept(this);
        System.out.print(" = ");
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
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" + ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" - ");
        n.e2.accept(this);
        System.out.print(")");
    }

    // Exp e1,e2;
    public void visit(Times n) {
        System.out.print("(");
        n.e1.accept(this);
        System.out.print(" * ");
        n.e2.accept(this);
        System.out.print(")");
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
        System.out.print(n.i);
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
        System.out.print("new ");
        System.out.print(n.i.s);
        System.out.print("()");
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
