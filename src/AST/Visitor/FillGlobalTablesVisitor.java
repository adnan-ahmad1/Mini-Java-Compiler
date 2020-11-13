package AST.Visitor;

import AST.*;
import Semantics.ClassSemanticTable;
import Semantics.MethodSemanticTable;
import Semantics.SemanticTable;

public class FillGlobalTablesVisitor implements Visitor {
    SemanticTable semanticTable;

    public FillGlobalTablesVisitor(SemanticTable semanticTable) {
        this.semanticTable = semanticTable;
    }

    public SemanticTable getSemanticTable() {
        return this.semanticTable;
    }

    // Display added for toy example language.  Not used in regular MiniJava
    public void visit(Display n) {
        System.out.print("display ");
        n.e.accept(this);
        System.out.print(";");
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            System.out.println();
            n.cl.get(i).accept(this);
        }
        semanticTable.setSuperClasses();
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {

        // add class name
        semanticTable.addClass(n.i1.toString());
        semanticTable.goIntoClass(n.i1.toString());

        // add main method
        semanticTable.getCurrClassTable().addMethod("main",
                      new MethodSemanticTable(semanticTable.getCurrClassTable()));

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

        boolean exists = false;

        if (n.t instanceof IntegerType) {
            exists = semanticTable.getCurrTable().addPrimitiveVariable(n.i.toString(), Semantics.IntegerType.getInstance());
        } else if (n.t instanceof IntArrayType) {
            exists = semanticTable.getCurrTable().addPrimitiveVariable(n.i.toString(), Semantics.IntArrayType.getInstance());
        } else if (n.t instanceof BooleanType ) {
            exists = semanticTable.getCurrTable().addPrimitiveVariable(n.i.toString(), Semantics.BooleanType.getInstance());
        } else if (n.t instanceof IdentifierType) {
            exists = semanticTable.getCurrTable().addClassVariable(n.i.toString(), ((IdentifierType)n.t).s);
        }

        // error handle
        if (exists) {
            System.out.println("Variable " + n.i.toString() + " already exists");
        }

    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {

        // add method to symbol table
        semanticTable.getCurrClassTable().addMethod(n.i.s,
                      new MethodSemanticTable(semanticTable.getCurrClassTable()));
        semanticTable.goIntoMethod(n.i.s);

        // go through list to add parameters
        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.get(i).accept(this);
        }

        // variable declarations in method
        for ( int i = 0; i < n.vl.size(); i++ ) {
            n.vl.get(i).accept(this);
        }

    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {

        boolean exists = false;
        if (n.t instanceof IntegerType) {
            exists = semanticTable.getCurrMethodTable().addPrimParam(n.i.toString(), Semantics.IntegerType.getInstance());
        } else if (n.t instanceof IntArrayType) {
            exists = semanticTable.getCurrMethodTable().addPrimParam(n.i.toString(), Semantics.IntArrayType.getInstance());
        } else if (n.t instanceof BooleanType ) {
            exists = semanticTable.getCurrMethodTable().addPrimParam(n.i.toString(), Semantics.BooleanType.getInstance());
        } else if (n.t instanceof IdentifierType) {
            exists = semanticTable.getCurrMethodTable().addClassParam(n.i.toString(), ((IdentifierType)n.t).s);
        }
        if (exists) {
            System.out.println("Parameter " + n.i.toString() + " already defined.");
        }
        /*
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);

         */
    }

    public void visit(IntArrayType n) {
    }

    public void visit(BooleanType n) {
    }

    public void visit(IntegerType n) {
    }

    // String s;
    public void visit(IdentifierType n) {
    }

    // StatementList sl;
    public void visit(Block n) {
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
    }

    // Exp e;
    public void visit(Print n) {
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
    }

    // Exp e1,e2;
    public void visit(And n) {
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
    }

    // Exp e1,e2;
    public void visit(Plus n) {
    }

    // Exp e1,e2;
    public void visit(Minus n) {
    }

    // Exp e1,e2;
    public void visit(Times n) {
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
    }

    // Exp e;
    public void visit(ArrayLength n) {
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
    }

    // int i;
    public void visit(IntegerLiteral n) {
    }

    public void visit(True n) {

    }

    public void visit(False n) {

    }

    // String s;
    public void visit(IdentifierExp n) { }

    public void visit(This n) { }

    // Exp e;
    public void visit(NewArray n) {
    }

    // Identifier i;
    public void visit(NewObject n) {
    }

    // Exp e;
    public void visit(Not n) { }

    // String s;
    public void visit(Identifier n) { }
}
