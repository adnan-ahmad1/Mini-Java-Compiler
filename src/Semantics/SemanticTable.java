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
    }

    public ClassSemanticTable getCurrClassTable() {
        return currClass;
    }

    public void goIntoMethod(String methodName){
        if (currClass.containsKey(methodName)) {
            currMethod = methods.get(methodName);
        } else {
            currMethod = null;
        }
    }

    public MethodSemanticTable getCurrMethodTable() {
        return currMethod;
    }

}
