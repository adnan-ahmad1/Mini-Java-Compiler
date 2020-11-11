package Semantics;

import java.util.*;

public class SemanticTable {
    private Map<String, ClassSemanticTable> classes;
    private ClassSemanticTable currClass;

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

}
