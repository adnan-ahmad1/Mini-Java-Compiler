package Semantics;

import java.util.*;

public class SemanticTable {
    private Map<String, List<String>> superToSubclass;
    private Map<String, String> subclassToSuperClass;
    private Map<String, ClassSemanticTable> classes;
    private ClassSemanticTable currClass;

    void addClass(String className){}
    void goIntoClass(String className){}
    ClassSemanticTable getCurrClassTable() {return null;}
    String getSuperClass(String className){return null;}
    List<String> getSubClass(String className){return null;}
    // Dog var = new Cat()
}
