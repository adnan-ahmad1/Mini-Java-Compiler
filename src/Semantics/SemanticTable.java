package Semantics;

import java.util.*;

public class SemanticTable {
    private Map<String, ClassSemanticTable> classes;
    private ClassSemanticTable currClass;
    private MethodSemanticTable currMethod;

    public SemanticTable() {
        classes = new HashMap<>();
    }

    public boolean addClass(String className) {
        if (!classes.containsKey(className)) {
            classes.put(className, new ClassSemanticTable(className));
            return true;
        }

        return false;
    }

    public void goIntoClass(String className){
        if (classes.containsKey(className)) {
            currClass = classes.get(className);
        } else {
            currClass = null;
        }
        currMethod = null;
    }

    public ClassSemanticTable getCurrClassTable() {
        return currClass;
    }

    public MethodSemanticTable getCurrMethodTable() {return currMethod;}

    public void goIntoMethod(String methodName){
        if (currClass == null) {
            return;
        }
        currMethod = currClass.getMethod(methodName);
    }

    public Table getCurrTable() {
        if (currMethod != null) {
            return currMethod;
        }
        return currClass;
    }



    public boolean setSuperClasses() {
        for(String c: classes.keySet()) {
            String currSuper = classes.get(c).getSuperClassName();
            if (currSuper != null) {
                if (classes.containsKey(currSuper)) {
                    classes.get(c).setSuperClass(classes.get(currSuper));
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void printTable() {
        for(String c : classes.keySet()) {
            classes.get(c).printTable();
        }
    }

    public boolean typeExists(String type) {
        return classes.containsKey(type);
    }

    public Type getType(String className){
        if (classes.containsKey(className)) {
            return classes.get(className);
        }
        return null;
    }

    public boolean containsClass(String className) {
        if (classes.keySet().contains(className)) {
            return true;
        }
        return false;
    }
}
