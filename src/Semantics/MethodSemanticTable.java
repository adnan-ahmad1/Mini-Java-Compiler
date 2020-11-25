package Semantics;

import java.util.*;

public class MethodSemanticTable extends Table{
    private List<String> paramOrder;
    private Map<String, Type> parameters;
    private ClassSemanticTable classInside;
    private Type returnType;
    private String name;

    public MethodSemanticTable(String name, ClassSemanticTable classInside, Type returnType) {
        super();
        this.classInside = classInside;
        parameters = new HashMap<>();
        paramOrder = new LinkedList<>();
        this.returnType = returnType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean addVariable(String variable, Type type) {
        return super.addVariable(variable, type);
    }

    public boolean addParam(String name, Type type) {
        if (parameters.containsKey(name)) {
            return false;
        }
        parameters.put(name, type);
        paramOrder.add(name);
        return true;
    }

    public List<String> getParaOrder() {
        return paramOrder;
    }

    public Map<String, Type> getParameters() {
        return parameters;
    }

    public void printTable() {
        System.out.println("    " + name);
        System.out.println("      return type: " + getReturnType().toString());
        System.out.println("      parameters:");
        for (String params : paramOrder) {
            System.out.println("        " + parameters.get(params).toString() + " " + params);
        }
        System.out.println("      variables:");
        for (String var : variables.keySet()) {
            System.out.println("        " + this.getVarType(var).toString() + " " +var);
        }
    }

    @Override
    public boolean containsVariable(String variable) {
        if (super.containsVariable(variable) || paramOrder.contains(variable)) {
            return true;
        }
        return classInside.containsVariable(variable);
    }

    @Override
    public Type getVarType(String variable) {
        if (paramOrder.contains(variable)) {
            return parameters.get(variable);
        }
        if (super.containsVariable(variable)) {
            return super.getVarType(variable);
        }
        return classInside.getVarType(variable);
    }

    public Type getReturnType() {
        if (returnType == null) {
            return Semantics.Unknown.getInstance();
        }
        return returnType;
    }

    public boolean callParamsOrdered(List<Type> callParams) {
        int i = 0;
        for (String s : paramOrder) {

            // too few args passed in
            if (i >= callParams.size()) {
                return false;
            }

            Type t = parameters.get(s);
            Type callParam = callParams.get(i);

            if (t instanceof ClassSemanticTable) {
                ClassSemanticTable c = ((ClassSemanticTable)t);
                if (!callParam.equals(c) && !c.isSubtype(callParam)) {
                    return false;
                }
            } else if (!callParam.equals(t)) {
                return false;
            }

            i++;
        }

        // arg list is too long
        if (i != callParams.size()) {
            return false;
        }
        return true;
    }

    public ClassSemanticTable getClassInside() {
        return classInside;
    }
}
