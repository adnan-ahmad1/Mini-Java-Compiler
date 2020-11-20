package Semantics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassSemanticTable extends Table implements Type{
    private Map<String, MethodSemanticTable> methods;
    private String className;
    private String superClassName;
    private ClassSemanticTable superClass;

    public ClassSemanticTable(String name) {
        super();
        className = name;
        methods = new HashMap<>();
    }

    public boolean equals(Type type) {
        if (type instanceof ClassSemanticTable) {
            return this.className.equals(type.toString());
        }
        return false;
    }

    public boolean isSubtype(Type type) {
        ClassSemanticTable c = ((ClassSemanticTable)(type));

        if (c.getSuperClass() != null && c.getSuperClass().equals(this)) {
            return true;
        } else if (c.getSuperClass() != null) {
            return isSubtype(c.getSuperClass());
        } else {
            return false;
        }
    }

    public String toString() {
        return className;
    }

    public boolean addMethod(String method, MethodSemanticTable type) {
        if (!methods.containsKey(method)) {
            methods.put(method, type);
            return true;
        }
        return false;
    }

    public Set<String> getMethodNames() {
        return methods.keySet();
    }

    @Override
    public boolean addVariable(String variable, Type type) {
        if (superClass == null || !superClass.containsVariable(variable)) {
            return super.addVariable(variable, type);
        }
        return false;
    }

    public ClassSemanticTable getSuperClass() {
        return superClass;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClass) {
        this.superClassName = superClass;
    }

    public void setSuperClass(ClassSemanticTable superClass) {
        this.superClass = superClass;
    }

    public String getName(){return className;}

    public MethodSemanticTable getMethod(String method) {
        if (containsMethod(method)) {
            if (methods.containsKey(method)) {
                return methods.get(method);
            }
            return superClass.getMethod(method);
        }
        return null;
    }

    @Override
    public boolean containsVariable(String variable) {
        if (super.containsVariable(variable)) {
            return true;
        }
        if (superClass != null && superClass.containsVariable(variable)) {
            return true;
        }
        return false;
    }

    @Override
    public Type getVarType(String variable) {
        if (super.containsVariable(variable)) {
            return super.getVarType(variable);
        }
        if (superClass != null && superClass.containsVariable(variable)) {
            return superClass.getVarType(variable);
        }
        return null;
    }

    public boolean containsMethod(String method) {
        if (methods.containsKey(method)) {
            return true;
        }
        if (superClass != null && superClass.containsMethod(method)) {
            return true;
        }
        return false;
    }

    public void printTable() {
        if (superClassName == null) {
            System.out.println("Class "+ className);
        } else {
            System.out.println("Class " + className + " extends " + superClassName);
        }
        System.out.println("  fields:");
        for (String var : variables.keySet()) {
            System.out.println("    "+ variables.get(var).toString() + " " + var);
        }
        System.out.println("  methods:");
        for(String method : methods.keySet()) {
            methods.get(method).printTable();
        }
    }

}
