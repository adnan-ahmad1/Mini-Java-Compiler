package Semantics;

import AST.Identifier;

import java.util.*;

public class MethodSemanticTable extends Table{
    private List<String> parameters;
    private Map<String, Type> primitiveParamters;
    private Map<String, String> classParameters;
    private Stack<String> scopeVars;
    private ClassSemanticTable classInside;

    public MethodSemanticTable(ClassSemanticTable classInside) {
        super();
        this.classInside = classInside;
        parameters = new ArrayList<>();
        primitiveParamters = new HashMap<>();
        classParameters = new HashMap<>();
        scopeVars = new Stack<>();
    }

    public boolean addPrimParam(String name, Type type) {
        if (primitiveParamters.containsKey(name)) {
            return false;
        }
        parameters.add(name);
        primitiveParamters.put(name, type);
        return true;
    }
    // int a;
    // a = 4;
    public boolean addClassParam(String name, String className) {
        if (classParameters.containsKey(name)) {
            return false;
        }
        parameters.add(name);
        classParameters.put(name, className);
        return true;
    }

    @Override
    public boolean addClassVariable(String variable, String type) {
        if (!classInside.containsVariable(variable)) {
            return false;
        }
        boolean successfully_add = super.addClassVariable(variable, type);
        if (successfully_add) {
            scopeVars.push(variable);
        }
        return successfully_add;
    }

    @Override
    public boolean addPrimitiveVariable(String variable, Type type) {
        if (!classInside.containsVariable(variable)) {
            return false;
        }
        boolean successfully_add = super.addPrimitiveVariable(variable, type);
        if (successfully_add) {
            scopeVars.push(variable);
        }
        return successfully_add;
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

        if (primitiveParamters.containsKey(s)) {
            primitiveParamters.remove(s);
        }

        if (classParameters.containsKey(s)) {
            classParameters.remove(s);
        }

        return true;
    }

    public List<String> getParameters() {
        return parameters;
    }


    public Map<String, Type> getPrimitiveParamters() {
        return primitiveParamters;
    }


    public Map<String, String> getClassParameters() {
        return classParameters;
    }

}
