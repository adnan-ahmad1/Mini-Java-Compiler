package Semantics;

import AST.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodSemanticTable {
    private List<String> parameters;
    private Map<String, Type> primitiveParamters;
    private Map<String, String> classParameters;

    private Map<String, Type> localPrimitiveVariables;
    private Map<String, String> localClassVariables;

    public MethodSemanticTable() {
        parameters = new ArrayList<>();
        primitiveParamters = new HashMap<>();
        classParameters = new HashMap<>();
        localPrimitiveVariables = new HashMap<>();
        localClassVariables = new HashMap<>();
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Type> getPrimitiveParamters() {
        return primitiveParamters;
    }

    public void setPrimitiveParamters(Map<String, Type> primitiveParamters) {
        this.primitiveParamters = primitiveParamters;
    }

    public Map<String, String> getClassParameters() {
        return classParameters;
    }

    public void setClassParameters(Map<String, String> classParameters) {
        this.classParameters = classParameters;
    }

    public Map<String, Type> getLocalPrimitiveVariables() {
        return localPrimitiveVariables;
    }

    public void setLocalPrimitiveVariables(Map<String, Type> localPrimitiveVariables) {
        this.localPrimitiveVariables = localPrimitiveVariables;
    }

    public Map<String, String> getLocalClassVariables() {
        return localClassVariables;
    }

    public void setLocalClassVariables(Map<String, String> localClassVariables) {
        this.localClassVariables = localClassVariables;
    }
}
