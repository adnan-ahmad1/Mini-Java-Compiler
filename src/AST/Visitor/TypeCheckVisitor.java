package AST.Visitor;

import AST.*;
import Semantics.ClassSemanticTable;
import Semantics.SemanticTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeCheckVisitor implements Visitor {
    private SemanticTable semanticTable;

    public TypeCheckVisitor(SemanticTable semanticTable) {
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

        // go through list of parameters and make sure types exist
        Map<String, Semantics.Type> parameters = semanticTable.getCurrMethodTable().getParameters();
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                if (parameters.get(key) instanceof Semantics.Unknown) {
                    //error handle
                    System.out.print("ERROR! Line Number: " + n.line_number + ", ");
                    System.out.println("Parameter " + key + " has class type that does not exist!");
                    semanticTable.setError();
                }
            }
        }

        // type check statements!
        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
        }

        // make sure return type is correct
        n.e.accept(this);

        Semantics.Type t = semanticTable.getCurrMethodTable().getReturnType();

        if (t.equals(Semantics.Unknown.getInstance())) {
            //error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Return type for method definiton is unknown!");
            semanticTable.setError();
            return;
        }

        if (t instanceof ClassSemanticTable) {
            ClassSemanticTable c = ((ClassSemanticTable)t);
            if (!n.e.type.equals(c) && !c.isSubtype(n.e.type)) {
                //error handle
                System.out.print("ERROR! Line Number: " + n.line_number + ", ");
                System.out.println("Return type should be "
                        + semanticTable.getCurrMethodTable().getReturnType().toString() + " but was " + n.e.type.toString());
                semanticTable.setError();
            }
        } else if (!n.e.type.equals(t)) {
            //error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Return type should be "
                    + semanticTable.getCurrMethodTable().getReturnType().toString() + " but was " + n.e.type.toString());
            semanticTable.setError();
        }
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

        if (!n.e.type.equals(Semantics.BooleanType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("If: Type in parentheses was " + n.e.type.toString());
            semanticTable.setError();
        }

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

        if (!n.e.type.equals(Semantics.BooleanType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("While: Type in parentheses was " + n.e.type.toString());
            semanticTable.setError();
        }

        // type check statement
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {

        // type check expression
        n.e.accept(this);
        if (!n.e.type.equals(Semantics.IntegerType.getInstance())) {
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Only integer types can be printed");
            semanticTable.setError();
        }
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {

        // make sure identifier exists
        n.i.accept(this);

        if (!semanticTable.getCurrMethodTable().containsVariable(n.i.s)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Assign: Variable " + n.i.s + " does not exist!");
            semanticTable.setError();
        }

        // type check expression and make sure its
        // type is appropriate with the identifier type
        n.e.accept(this);

        Semantics.Type t = semanticTable.getCurrMethodTable().getVarType(n.i.s);
        if (t.equals(Semantics.Unknown.getInstance()) && n.e.type.equals(Semantics.Unknown.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Assign: You are assigning an unknown to an unknown!");
            semanticTable.setError();
        } else if (t instanceof ClassSemanticTable) {
            ClassSemanticTable c = ((ClassSemanticTable) t);
            if (!c.equals(n.e.type) && !c.isSubtype(n.e.type)) {
                // error handle
                System.out.print("ERROR! Line Number: " + n.line_number + ", ");
                System.out.println("Assign: Variable type for " + n.i.s + " is "
                        + t.toString() + " but was assigned type " + n.e.type.toString());
                semanticTable.setError();
            }
        } else if (!t.equals(n.e.type)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Assign: Variable type for " + n.i.s + " is "
                    + t.toString() + " but was assigned type " + n.e.type.toString());
            semanticTable.setError();
        }
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {

        // make sure variable exists and is of type IntArray
        n.i.accept(this);

        if (!semanticTable.getCurrMethodTable().containsVariable(n.i.s)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayAssign: Variable " + n.i.s + " does not exist!");
            semanticTable.setError();
        }

        if (!semanticTable.getCurrMethodTable().getVarType(n.i.s).equals(Semantics.IntArrayType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayAssign: Variable " + n.i.s + " is not IntArrayType");
            semanticTable.setError();
        }

        // type check expression and make sure its of type int
        n.e1.accept(this);

        if (!n.e1.type.equals(Semantics.IntegerType.getInstance())) {
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayAssign: Expression1 is not IntegerType");
            semanticTable.setError();
        }

        // type check expression and make sure its of type int
        n.e2.accept(this);

        if (!n.e2.type.equals(Semantics.IntegerType.getInstance())) {
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayAssign: Expression2 is not IntegerType");
            semanticTable.setError();
        }
    }

    // Exp e1,e2;
    public void visit(And n) {

        boolean error = false;
        // type check expression and make sure its of type boolean
        n.e1.accept(this);

        if (!n.e1.type.equals(Semantics.BooleanType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("And: Expression1 is not BooleanType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        // type check expression and make sure its of type boolean
        n.e2.accept(this);

        if (!n.e2.type.equals(Semantics.BooleanType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("And: Expression2 is not BooleanType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        if (!error) {
            n.type = Semantics.BooleanType.getInstance();
        }
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        boolean error = false;

        // type check expression and make sure its of type int
        n.e1.accept(this);

        if (!n.e1.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("LessThan: Expression1 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        // type check expression and make sure its of type int
        n.e2.accept(this);

        if (!n.e2.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("LessThan: Expression2 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        if (!error) {
            n.type = Semantics.BooleanType.getInstance();
        }
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        boolean error = false;

        // type check expression and make sure its of type int
        n.e1.accept(this);

        if (!n.e1.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Plus: Expression1 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        // type check expression and make sure its of type int
        n.e2.accept(this);

        if (!n.e2.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Plus: Expression2 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        if (!error) {
            n.type = Semantics.IntegerType.getInstance();
        }
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        boolean error = false;

        // type check expression and make sure its of type int
        n.e1.accept(this);
        if (!n.e1.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Minus: Expression1 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        // type check expression and make sure its of type int
        n.e2.accept(this);
        if (!n.e2.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Minus: Expression2 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        if (!error) {
            n.type = Semantics.IntegerType.getInstance();
        }
    }

    // Exp e1,e2;
    public void visit(Times n) {
        boolean error = false;

        // type check expression and make sure its of type int
        n.e1.accept(this);
        if (!n.e1.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Times: Expression1 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        // type check expression and make sure its of type int
        n.e2.accept(this);
        if (!n.e2.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Times: Expression2 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        if (!error) {
            n.type = Semantics.IntegerType.getInstance();
        }
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        boolean error = false;

        // type check expression and make sure its a variable
        // that exists of type IntArray
        n.e1.accept(this);

        if (!n.e1.type.equals(Semantics.IntArrayType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayLookup: Expression1 is not IntArrayType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        // type check expression and make sure its of type int
        n.e2.accept(this);

        if (!n.e2.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayLookup: Expression2 is not IntegerType");
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            error = true;
        }

        if (!error) {
            n.type = Semantics.IntegerType.getInstance();
        }
    }

    // Exp e;
    public void visit(ArrayLength n) {

        // type check expression and make sure its of type IntArray
        n.e.accept(this);

        if (!n.e.type.equals(Semantics.IntArrayType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("ArrayLength: Expression is not IntArrayType");
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
        } else {
            n.type = Semantics.IntegerType.getInstance();
        }
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {

        // type check expression and make sure its a variable
        n.e.accept(this);

        // make sure its a class type
        if (!(n.e.type instanceof ClassSemanticTable)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Call: Expression is not a ClassType");
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            return;
        }

        ClassSemanticTable c = (ClassSemanticTable)(n.e.type);

        // make sure this is a method that exists in variable's type
        if (!c.containsMethod(n.i.s)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Call: Method " + n.i.s + " does not exist for type " + c.toString());
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
            return;
        }

        List<Semantics.Type> typeList = new ArrayList<>();

        // make sure expression type corresponds to parameters of method
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.get(i).accept(this);
            typeList.add(n.el.get(i).type);
        }

        if (!c.getMethod(n.i.s).callParamsOrdered(typeList)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Call: Number or type of arguments is incorrect for method "
                    + n.i.s + " of class " + c.getName());
            n.type = Semantics.Unknown.getInstance();
            semanticTable.setError();
            return;
        }

        n.type = c.getMethod(n.i.s).getReturnType();
    }

    // int i;
    public void visit(IntegerLiteral n) {
        // number so return of type int
        n.type = Semantics.IntegerType.getInstance();
    }

    public void visit(True n) {
        // return of type boolean
        n.type = Semantics.BooleanType.getInstance();
    }

    public void visit(False n) {
        // return of type boolean
        n.type = Semantics.BooleanType.getInstance();
    }

    // String s;
    public void visit(IdentifierExp n) {

        // make sure variable is declared
        if (!semanticTable.getCurrMethodTable().containsVariable(n.s)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("IdentifierExp: Variable " + n.s + " does not exist!");
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
        } else {
            // set expression node type to class semantic table
            n.type = semanticTable.getCurrMethodTable().getVarType(n.s);
        }
    }

    public void visit(This n) {
        n.type = semanticTable.getCurrClassTable();
    }

    // Exp e;
    public void visit(NewArray n) {

        // type check expression and make sure its of type int
        n.e.accept(this);

        if (!n.e.type.equals(Semantics.IntegerType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("NewArray: Expression is not IntegerType!");
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
        } else {
            n.type = Semantics.IntArrayType.getInstance();
        }
    }

    // Identifier i;
    public void visit(NewObject n) {

        // make sure class type exists
        if (!semanticTable.containsClass(n.i.s)) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("NewObject: Class " + n.i.s + " is unknown!");
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
        } else {
            n.type = semanticTable.getClass(n.i.s);
        }
    }

    // Exp e;
    public void visit(Not n) {

        // type check expression and make sure its of type boolean
        n.e.accept(this);

        if (!n.e.type.equals(Semantics.BooleanType.getInstance())) {
            // error handle
            System.out.print("ERROR! Line Number: " + n.line_number + ", ");
            System.out.println("Not: Expression is not BooleanType! ");
            semanticTable.setError();
            n.type = Semantics.Unknown.getInstance();
        } else {
            n.type = Semantics.BooleanType.getInstance();
        }
    }

    // String s;
    public void visit(Identifier n) {
    }
}
