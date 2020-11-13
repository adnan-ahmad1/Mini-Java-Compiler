package Semantics;

import java.util.HashMap;
import java.util.Map;

public class ClassSemanticTable extends Table {
    private Map<String, MethodSemanticTable> methods;
    private String className;
    private String superClassName;
    private ClassSemanticTable superClass;

    public ClassSemanticTable(String name) {
        super();
        className = name;
        methods = new HashMap<>();
    }

    public boolean addMethod(String method, MethodSemanticTable type) {
        if (!methods.containsKey(method)) {
            methods.put(method, type);
            return true;
        }
        return false;
    }

    @Override
    public boolean addPrimitiveVariable(String variable, Type type) {
        if (superClass == null )
        return super.addPrimitiveVariable(variable, type);
    }

    public ClassSemanticTable getSuperClass() {
        return superClass;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClass) {
        if (superClass.equals(className)) {
            //error handling
        }
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

    public boolean containsVariable(String variable) {
        if (superClass != null && superClass.containsVariable(variable)) {
            return true;
        }
        if (primitiveVariables.containsKey(variable) ||
            classVariables.containsKey(variable)) {
            return true;
        }
        return false;
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
}
