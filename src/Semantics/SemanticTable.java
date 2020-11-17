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
        Set<String> superClassSet = new HashSet<>();
        Stack<ClassSemanticTable> curr = new Stack<>();
        for(String c : classes.keySet()) {
            if (superClassSet.contains(c)) {
                continue;
            }
            Set<String> seen  = new HashSet<>();
            curr.push(classes.get(c));
            seen.add(c);
            while(curr.peek().getSuperClassName() != null) {
                String superClassName = curr.peek().getSuperClassName();
                if (!classes.containsKey(superClassName)) {
                    // Super class does not exist
                    // error handle
                    System.out.println("Class " + superClassName + " does not exist");
                    curr.peek().setSuperClassName(null);
                    break;
                }
                if (seen.contains(superClassName)) {
                    // error handle
                    // Cycle exists
                    System.out.println(superClassName + " is its own superclass");
                    return false;
                }
                curr.push(classes.get(superClassName));
                seen.add(superClassName);
            }
            superClassSet.add(curr.pop().getName());
            while (!curr.empty()) {
                ClassSemanticTable currT = curr.pop();
                superClassSet.add(currT.getName());
                ClassSemanticTable supClass = classes.get(currT.getSuperClassName());
                currT.setSuperClass(supClass);
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

    public ClassSemanticTable getClass(String className) {
        if (containsClass(className)) {
            return classes.get(className);
        }
        return null;
    }

    public void checkOverrides() {
        for (String c : classes.keySet()) {
            if (classes.get(c).getSuperClass() == null) {
                continue;
            }
            for (String method : classes.get(c).getMethodNames()) {
                MethodSemanticTable methodInfo = classes.get(c).getMethod(method);
                ClassSemanticTable supClass = classes.get(c).getSuperClass();
                while (supClass != null || !supClass.containsMethod(method)) {
                    supClass = supClass.getSuperClass();
                }
                if (supClass == null) {
                    continue;
                }
                equalMethodInfo(supClass.getMethod(method), methodInfo);
            }
        }
    }

    private boolean equalMethodInfo(MethodSemanticTable tab1, MethodSemanticTable tab2) {
        boolean equals = true;
        if (tab1.getParaOrder().size() != tab2.getParaOrder().size()) {
            System.out.println("Override method does not have the same number of parameters as super class method.");
        }
        for (int i = 0; i < tab1.getParaOrder().size(); i++) {
            if (tab1.getParaOrder().get(i).equals(tab2.getParaOrder().get(i))) {
                if (!tab1.getVarType(tab1.getParaOrder().get(i)).equals(tab2.getVarType(tab2.getParaOrder().get(i)))) {
                    equals = false;
                    System.out.println("Parameter "+ i + " is not of same type as super class method");
                }
            } else {
                equals = false;
                System.out.println("Parameter " + i + " does not have same name");
            }
        }
        if (!tab1.getReturnType().equals(tab2.getReturnType())) {
            equals = false;
            System.out.println("Override method does not have the same return type as super class method");
        }
        return equals;
    }
}
