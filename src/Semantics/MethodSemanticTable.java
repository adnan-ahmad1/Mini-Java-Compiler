package Semantics;

import AST.Identifier;

import java.util.*;

public class MethodSemanticTable extends Table{
    private List<String> parameters;
    private Map<String, Type> primitiveParamters;
    private Map<String, String> classParameters;
    private Stack<String> scopeVars;

    public MethodSemanticTable() {
        super();
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
        scopeVars.push(name);
        return true;
    }

    public boolean addClassParam(String name, String className) {
        if (classParameters.containsKey(name)) {
            return false;
        }
        parameters.add(name);
        classParameters.put(name, className);
        scopeVars.push(name);
        return true;
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
