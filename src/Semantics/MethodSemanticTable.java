package Semantics;

import AST.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodSemanticTable extends Table{
    private List<String> parameters;
    private Map<String, Type> primitiveParamters;
    private Map<String, String> classParameters;

    public MethodSemanticTable() {
        super();
        parameters = new ArrayList<>();
        primitiveParamters = new HashMap<>();
        classParameters = new HashMap<>();
    }

    public boolean addPrimParam(String name, Type type) {
        if (primitiveParamters.containsKey(name)) {
            return false;
        }
        parameters.add(name);
        primitiveParamters.put(name, type);
        return true;
    }

    public boolean addClassParam(String name, String className) {
        if (classParameters.containsKey(name)) {
            return false;
        }
        parameters.add(name);
        classParameters.put(name, className);
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
