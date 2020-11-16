package AST.Visitor;

import AST.*;
import Semantics.SemanticTable;

public class FillLocalTablesAndTypeCheckVisitor implements Visitor {
    private SemanticTable semanticTable;

    public FillLocalTablesAndTypeCheckVisitor(SemanticTable semanticTable) {
        this.semanticTable = semanticTable;
    }

    public SemanticTable getSemanticTable() {
        return this.semanticTable;
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
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {

        // type check statements
        n.s.accept(this);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {

        // go into class
        semanticTable.goIntoClass(n.i.toString());

        // go into methods to add local variables and type check
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
        }

    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {

        // go into class
        semanticTable.goIntoClass(n.i.toString());

        // go into methods to add local variables and type check
        for ( int i = 0; i < n.ml.size(); i++ ) {
            n.ml.get(i).accept(this);
        }
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {

    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {

        semanticTable.goIntoMethod(n.i.s);

        // type check statements!
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
        }

        // make sure return type is correct
        n.e.accept(this);
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
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

        // type check list of statements
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
        }
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        // type check expression and make sure it is of type boolean
        n.e.accept(this);

        // type check statement
        n.s1.accept(this);

        // type check statement
        n.s2.accept(this);
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        // type check expression and make sure it is of type boolean
        n.e.accept(this);

        // type check statement
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {

        // do we have to type check this?
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {

        // make sure identifier exists
        n.i.accept(this);

        // type check expression and make sure its
        // type is appropriate with the identifier type
        n.e.accept(this);
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {

        // make sure variable exists and is of type IntArray
        n.i.accept(this);

        // type check expression and make sure its of type int
        n.e1.accept(this);

        // type check expression and make sure its of type int
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(And n) {

        // type check expression and make sure its of type boolean
        n.e1.accept(this);

        // type check expression and make sure its of type boolean
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(LessThan n) {

        // type check expression and make sure its of type int
        n.e1.accept(this);

        // type check expression and make sure its of type int
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Plus n) {

        // type check expression and make sure its of type int
        n.e1.accept(this);

        // type check expression and make sure its of type int
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Minus n) {

        // type check expression and make sure its of type int
        n.e1.accept(this);

        // type check expression and make sure its of type int
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(Times n) {

        // type check expression and make sure its of type int
        n.e1.accept(this);

        // type check expression and make sure its of type int
        n.e2.accept(this);
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        // type check expression and make sure its a variable
        // that exists of type IntArray
        n.e1.accept(this);

        // type check expression and make sure its of type int
        n.e2.accept(this);
    }

    // Exp e;
    public void visit(ArrayLength n) {

        // type check expression and make sure its of type IntArray
        n.e.accept(this);
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {

        // type check expression and make sure its a variable
        n.e.accept(this);

        // make sure this is a method that exists in variable's type
        n.i.accept(this);

        // make sure expression type corresponds to parameters of method
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.get(i).accept(this);
        }
    }

    // int i;
    public void visit(IntegerLiteral n) {
        // number so return of type int
    }

    public void visit(True n) {
        // return of type boolean
    }

    public void visit(False n) {
        // return of type boolean
    }

    // String s;
    public void visit(IdentifierExp n) {
    }

    public void visit(This n) {
        System.out.print("this");
    }

    // Exp e;
    public void visit(NewArray n) {

        // type check expression and make sure its of type int
        n.e.accept(this);
    }

    // Identifier i;
    public void visit(NewObject n) {

        // make sure class type exists
        System.out.print(n.i.s);
    }

    // Exp e;
    public void visit(Not n) {

        // type check expression and make sure its of type boolean
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
        System.out.print(n.s);
    }
}
