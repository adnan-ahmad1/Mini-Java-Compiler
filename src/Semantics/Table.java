package Semantics;

import java.util.*;

public class Table {
    Map<String, PrimVarInfo> primitiveVariables;
    Map<String, ClassVarInfo> classVariables;

    public Table() {
        primitiveVariables = new HashMap<>();
        classVariables = new HashMap<>();
    }

    public boolean addPrimitiveVariable(String variable, Type type){
        if (!primitiveVariables.containsKey(variable)) {
            primitiveVariables.put(variable, new PrimVarInfo(type, false));
            return true;
        }

        return false;
    }

    public boolean defineVariable(String variable) {
        if (primitiveVariables.containsKey(variable)) {
            primitiveVariables.get(variable).defined = true;
        } else if (classVariables.containsKey(variable)) {
            classVariables.get(variable).defined = true;
        } else {
            return false;
        }
        return true;
    }

    public boolean isDefined(String variable) {
        if (primitiveVariables.containsKey(variable)) {
            return primitiveVariables.get(variable).defined;
        } else if (classVariables.containsKey(variable)) {
            return classVariables.containsKey(variable);
        }
        return false;
    }

    public boolean addClassVariable(String variable, String type){
        if (!classVariables.containsKey(variable)) {
            classVariables.put(variable, new ClassVarInfo(type, false));
            return true;
        }

        return false;
    }

    class PrimVarInfo {
        Type type;
        boolean defined;

        public  PrimVarInfo(Type type, boolean defined) {
            this.type = type;
            this.defined = defined;
        }

    }

    class ClassVarInfo {
        String classType;
        boolean defined;

        public ClassVarInfo(String type, boolean defined) {
            this.classType = type;
            this.defined = defined;
        }
    }
}
