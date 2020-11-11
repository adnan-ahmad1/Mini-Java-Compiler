package Semantics;

import java.util.HashMap;
import java.util.Map;

public class ClassSemanticTable extends Table {
    private Map<String, MethodSemanticTable> methods;
    private String className;
    private String superClass;

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

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getName(){return className;}

    public MethodSemanticTable getMethod(String method) {
        if (methods.containsKey(method)) {
            return methods.get(method);
        }
        return null;
    }
}
