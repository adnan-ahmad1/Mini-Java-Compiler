package AST.Visitor;

import AST.*;


public class AbstractTreeVisitor implements Visitor {
    public static StringBuilder indentBuilder = new StringBuilder();

    // Display added for toy example language.  Not used in regular MiniJava
    public void visit(Display n) {
        System.out.print("display ");
        n.e.accept(this);
        System.out.print(";");
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        indentBuilder.append("  ");
        System.out.println("Program");
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            System.out.println();
            n.cl.get(i).accept(this);
        }
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        System.out.print(indentBuilder.toString() + "MainClass ");
        indentBuilder.append("  ");
        n.i1.accept(this);
        System.out.print(" (line " + n.line_number + ")");
        System.out.println();
        n.s.accept(this);
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        System.out.print(indentBuilder.toString() + "Class ");
        indentBuilder.append("  ");
        n.i.accept(this);
        System.out.println();
        // var declarations
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.get(i).accept(this);
            System.out.println("  ");
        }

        // method declarations
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
            System.out.println();
        }

        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        System.out.print(indentBuilder.toString() + "Class ");
        indentBuilder.append("  ");
        n.i.accept(this);
        System.out.print(" extends ");
        n.j.accept(this);
        System.out.println(" (line " + n.line_number + ")");

        // var declarations
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.get(i).accept(this);
            System.out.println();
        }

        // method declarations
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
            System.out.println();
        }
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        System.out.print(indentBuilder.toString());
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
        System.out.print(indentBuilder.toString() + "MethodDecl ");
        indentBuilder.append("  ");
        n.i.accept(this);
        System.out.println(" (line " + n.line_number + ")");

        System.out.print(indentBuilder.toString() + "returns ");
        n.t.accept(this);
        System.out.println();

        // params
        if (n.fl.size() != 0) {
            System.out.println(indentBuilder.toString() + "parameters: ");
            indentBuilder.append("  ");

            for ( int i = 0; i < n.fl.size(); i++ ) {
                System.out.print(indentBuilder.toString());
                n.fl.get(i).accept(this);
                System.out.println();
            }
            indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
        }

        // variables
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.get(i).accept(this);
            System.out.println("");
        }

        // statements
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
        }

        // return expression
        System.out.print(indentBuilder.toString() + "Return ");
        n.e.accept(this);
        System.out.println(" (line " + n.e.line_number + ")");
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
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
            System.out.println();
        }
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        System.out.print(indentBuilder.toString() + "If ");
        n.e.accept(this);
        System.out.println();
        indentBuilder.append("  ");
        n.s1.accept(this);
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
        System.out.println();
        System.out.println(indentBuilder.toString() + "Else ");
        indentBuilder.append("  ");
        n.s2.accept(this);
        System.out.println();
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        System.out.print(indentBuilder.toString() + "While (line " + n.line_number + ")");
        indentBuilder.append("  ");
        System.out.print(indentBuilder.toString());
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
        n.e.accept(this);
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        System.out.println(indentBuilder.toString() + "Print (line " + n.line_number + ")");
        indentBuilder.append("  ");
        System.out.print(indentBuilder.toString());
        n.e.accept(this);
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        System.out.println(indentBuilder.toString() + "Assign (line " + n.line_number + ")");
        indentBuilder.append("  ");
        System.out.print(indentBuilder.toString());
        n.i.accept(this);
        System.out.print(" = ");
        n.e.accept(this);
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        System.out.println(indentBuilder.toString() + "Array assign (line " + n.line_number + ")");
        indentBuilder.append("  ");
        System.out.print(indentBuilder.toString());
        indentBuilder.delete(indentBuilder.length() - 2, indentBuilder.length());
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
