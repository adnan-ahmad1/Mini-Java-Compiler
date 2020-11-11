package AST.Visitor;

import AST.*;
import Semantics.MethodSemanticTable;
import Semantics.SemanticTable;

public class FillGlobalTablesVisitor implements Visitor {
    SemanticTable semanticTable = new SemanticTable();

    // Display added for toy example language.  Not used in regular MiniJava
    public void visit(Display n) {
        System.out.print("display ");
        n.e.accept(this);
        System.out.print(";");
    }

    public void visit(Program n, SemanticTable st){
        semanticTable = st;
        visit(n);
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            System.out.println();
            n.cl.get(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {

        // add class name
        semanticTable.addClass(n.i1.toString());
        semanticTable.goIntoClass(n.i1.toString());

        // add main method
        semanticTable.getCurrClassTable().addMethod("main", new MethodSemanticTable());

        // process statement
        n.s.accept(this);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {

        // add class to table
        semanticTable.addClass(n.i.toString());
        semanticTable.goIntoClass(n.i.toString());

        // process var declarations
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.get(i).accept(this);
        }

        // process method declarations
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
        }
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {

        // add class to table
        semanticTable.addClass(n.i.toString());
        semanticTable.goIntoClass(n.i.toString());

        semanticTable.getCurrClassTable().setSuperClass(n.j.toString());

        // process var declarations
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.get(i).accept(this);
        }

        // process method declarations
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
        }
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {

        if (n.t instanceof IntegerType) {

        }
        /*
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
        System.out.print(";");

         */


    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        System.out.print("  public ");
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
        System.out.print(" (");
        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.get(i).accept(this);
            if (i+1 < n.fl.size()) { System.out.print(", "); }
        }
        System.out.println(") { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            System.out.print("    ");
            n.vl.get(i).accept(this);
            System.out.println("");
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            System.out.print("    ");
            n.sl.get(i).accept(this);
            if ( i < n.sl.size() ) { System.out.println(""); }
        }
        System.out.print("    return ");
        n.e.accept(this);
        System.out.println(";");
        System.out.print("  }");
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
        System.out.print("System.out.println(");
        n.e.accept(this);
        System.out.print(");");
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.i.accept(this);
        System.out.print(" = ");
        n.e.accept(this);
        System.out.print(";");
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
