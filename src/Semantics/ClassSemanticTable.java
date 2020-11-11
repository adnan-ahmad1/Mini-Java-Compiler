package Semantics;

import java.util.HashMap;
import java.util.Map;

public class ClassSemanticTable {
    private Map<String, Type> primitiveVariables;
    private Map<String, String> classVariables;
    private Map<String, MethodSemanticTable> methods;

    private String className;
    private String superClass;

    public ClassSemanticTable(String name) {
        className = name;
        primitiveVariables = new HashMap<>();
        classVariables = new HashMap<>();
        methods = new HashMap<>();
    }

    public boolean addMethod(String method, MethodSemanticTable type) {
        if (!methods.containsKey(method)) {
            methods.put(method, type);
            return true;
        }
        return false;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public boolean addPrimitiveVariable(String variable, Type type){
        if (!primitiveVariables.containsKey(variable)) {
            primitiveVariables.put(variable, type);
            return true;
        }

        return false;
    }

    public boolean addClassVariable(String variable, String type){
        if (!classVariables.containsKey(variable)) {
            classVariables.put(variable, type);
            return true;
        }

        return false;
    }

    public MethodSemanticTable getMethodInfo(String method){return null;}
    public String getName(){return className;}
}
