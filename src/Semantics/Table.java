package Semantics;

import java.util.*;

public class Table {
    Map<String, Type> primitiveVariables;
    Map<String, String> classVariables;

    public Table() {
        primitiveVariables = new HashMap<>();
        classVariables = new HashMap<>();
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
}
