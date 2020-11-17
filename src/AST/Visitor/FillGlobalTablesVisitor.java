package AST.Visitor;

import AST.*;
import Semantics.ClassSemanticTable;
import Semantics.MethodSemanticTable;
import Semantics.SemanticTable;
import Semantics.Void;

public class FillGlobalTablesVisitor implements Visitor {
    private SemanticTable semanticTable;

    public FillGlobalTablesVisitor(SemanticTable semanticTable) {
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

        for (int i = 0; i < n.cl.size(); i++) {
            String className = n.cl.get(i).i.toString();

            // make sure redundant classes are not added
            if (semanticTable.containsClass(className)) {
                // error handle
                System.out.print("ERROR! Line Number: " + n.line_number + ", ");
                System.out.println("duplicate class " + className);
            }

            semanticTable.addClass(className);
        }

        for ( int i = 0; i < n.cl.size(); i++ ) {
            System.out.println();
            n.cl.get(i).accept(this);
        }

        if (!semanticTable.setSuperClasses()) {
            // error handle
            System.exit(1);
        }

        semanticTable.checkOverrides();
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {

        // add class name
        semanticTable.addClass(n.i1.toString());
        semanticTable.goIntoClass(n.i1.toString());

        // add main method
        Semantics.Type retMethodType = Void.getInstance();
        semanticTable.getCurrClassTable().addMethod("main",
                      new MethodSemanticTable(semanticTable.getCurrClassTable(), retMethodType));
        // process statement
        n.s.accept(this);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {

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
        semanticTable.goIntoClass(n.i.toString());

        semanticTable.getCurrClassTable().setSuperClassName(n.j.toString());

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

        boolean noExist = false;

        if (n.t instanceof IntegerType) {
            noExist = semanticTable.getCurrTable().addVariable(n.i.toString(), Semantics.IntegerType.getInstance());
        } else if (n.t instanceof IntArrayType) {
            noExist = semanticTable.getCurrTable().addVariable(n.i.toString(), Semantics.IntArrayType.getInstance());
        } else if (n.t instanceof BooleanType) {
            noExist = semanticTable.getCurrTable().addVariable(n.i.toString(), Semantics.BooleanType.getInstance());
        } else {
            if (!semanticTable.typeExists(((IdentifierType)n.t).s)) {
                noExist = semanticTable.getCurrTable().addVariable(n.i.toString(), Semantics.Unknown.getInstance());

                // error handle
                System.out.print("ERROR! Line Number: " + n.line_number + ", ");
                System.out.println("Type " + ((IdentifierType)n.t).s + " does NOT exist");
            } else {
                noExist = semanticTable.getCurrTable().addVariable(n.i.toString(), semanticTable.getType(((IdentifierType)n.t).s));
            }
        }

        // error handle
        if (!noExist) {
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
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
        if (semanticTable.getCurrClassTable().containsMethod(n.i.toString())) {
            //error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Method " + n.i.toString() + " already exists in " + semanticTable.getCurrClassTable().getName());
            return;
        }
        Semantics.Type returnType;
        if (n.t instanceof IntegerType) {
            returnType = Semantics.IntegerType.getInstance();
        } else if (n.t instanceof IntArrayType) {
            returnType = Semantics.IntArrayType.getInstance();
        } else if (n.t instanceof BooleanType ) {
            returnType = Semantics.BooleanType.getInstance();
        } else {
            returnType = semanticTable.getClass(((IdentifierType)(n.t)).s);
        }

        semanticTable.getCurrClassTable().addMethod(n.i.s,
                      new MethodSemanticTable(semanticTable.getCurrClassTable(), returnType));
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

        boolean noExist = false;

        if (n.t instanceof IntegerType) {
            noExist = semanticTable.getCurrMethodTable().addParam(n.i.toString(), Semantics.IntegerType.getInstance());
        } else if (n.t instanceof IntArrayType) {
            noExist = semanticTable.getCurrMethodTable().addParam(n.i.toString(), Semantics.IntArrayType.getInstance());
        } else if (n.t instanceof BooleanType ) {
            noExist = semanticTable.getCurrMethodTable().addParam(n.i.toString(), Semantics.BooleanType.getInstance());
        } else {
            if (!semanticTable.typeExists(((IdentifierType)n.t).s)) {
                noExist = semanticTable.getCurrMethodTable().addParam(n.i.toString(), Semantics.Unknown.getInstance());
                // error handle
                System.out.print("ERROR! Line Number: " + n.line_number + ", ");
                System.out.println("Type " + ((IdentifierType)n.t).s + " does NOT exist");
            } else {
                noExist = semanticTable.getCurrMethodTable().addParam(n.i.toString(), semanticTable.getType(((IdentifierType)n.t).s));
            }
        }

        // error handle
        if (!noExist) {
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Parameter " + n.i.toString() + " already exists");
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
