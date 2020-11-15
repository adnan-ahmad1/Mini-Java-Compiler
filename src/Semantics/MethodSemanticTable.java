package Semantics;

import AST.Identifier;

import java.util.*;

public class MethodSemanticTable extends Table{
    private List<String> paramOrder;
    private Map<String, Type> parameters;
    private Stack<String> scopeVars;
    private ClassSemanticTable classInside;
    private Type returnType;

    public MethodSemanticTable(ClassSemanticTable classInside, Type returnType) {
        super();
        this.classInside = classInside;
        parameters = new HashMap<>();
        paramOrder = new LinkedList<>();
        scopeVars = new Stack<>();
        this.returnType = returnType;
    }

    @Override
    public boolean addVariable(String variable, Type type) {
        boolean success = super.addVariable(variable, type);
        if(success) {
            scopeVars.push(variable);
        }
        return success;
    }

    public boolean addParam(String name, Type type) {
        if (parameters.containsKey(name)) {
            return false;
        }
        parameters.put(name, type);
        paramOrder.add(name);
        return true;
    }

    @Override
    public boolean defineVariable(String variable) {
        if (!super.defineVariable(variable)) {
            return classInside.defineVariable(variable);
        }
        return true;
    }

    @Override
    public boolean isDefined(String variable) {
        return super.isDefined(variable) || classInside.isDefined(variable);
    }

    public boolean removeTop() {
        String s = scopeVars.pop();
        if (variables.containsKey(s)) {
            variables.remove(s);
        }

        return true;
    }

    public List<String> getParaOrder() {
        return paramOrder;
    }


    public Map<String, Type> getParameters() {
        return parameters;
    }

    public void printTable(String name) {
        System.out.println("    " + name);
        System.out.println("      return type: " + returnType.toString());
        System.out.println("      parameters:");
        for (String params : paramOrder) {
            System.out.println("        " + parameters.get(params) + " " + params);
        }
        System.out.println("      variables:");
        for (String var : variables.keySet()) {
            System.out.println("        " + this.getVarType(var).toString() + " " +var);
        }
    }

    @Override
    public boolean containsVariable(String variable) {
        if (super.containsVariable(variable)) {
            return true;
        }
        return classInside.containsVariable(variable);
    }

    @Override
    public Type getVarType(String variable) {
        if (super.containsVariable(variable)) {
            return super.getVarType(variable);
        }
        return classInside.getVarType(variable);
    }
}