package Semantics;

import AST.Identifier;

import java.util.*;

public class SemanticTable {
    private Set<Type> baseTypes;
    private Map<String, IdentifierType> referenceTypes;
    private Map<String, ClassSemanticTable> classes;
    private ClassSemanticTable currClass;

    public SemanticTable() {
        baseTypes = new HashSet<>();
        referenceTypes = new HashMap<>();
        classes = new HashMap<>();
        baseTypes.add(BooleanType.getInstance());
        baseTypes.add(IntegerType.getInstance());
        baseTypes.add(StringType.getInstance());
        baseTypes.add(Void.getInstance());
        baseTypes.add(Unknown.getInstance());
    }

    void addClass(String className) {
        classes.put(className, new ClassSemanticTable());
        referenceTypes.put(className, new IdentifierType(className));
    }

    void goIntoClass(String className){
        if (classes.containsKey(className)) {
            currClass = classes.get(className);
        } else {
            currClass = null;
        }
    }

    ClassSemanticTable getCurrClassTable() {
        return currClass;
    }

    IdentifierType getSuperClass(String className){
        if (referenceTypes.containsKey(className)) {
            return referenceTypes.get(className).superclass;
        }
        return null;
    }
    // Dog var = new Cat()
}
