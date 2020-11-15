package Semantics;

import java.util.*;

public class Table {
    Map<String, VarInfo>  variables;

    public Table() {
        variables = new HashMap<>();
    }

    public boolean addVariable(String variable, Type type){
        if (variables.containsKey(variable)) {
            return false;
        }
        variables.put(variable, new VarInfo(type, false));
        return true;
    }

    public boolean containsVariable(String variable) {
        if (variables.containsKey(variable)) {
            return true;
        }
        return false;
    }

    public Type getVarType(String variable) {
        if (variables.containsKey(variable)) {
            return variables.get(variable).type;
        }
        return null;
    }

    public boolean defineVariable(String variable) {
        if (variables.containsKey(variable)) {
            variables.get(variable).defined = true;
        } else {
            return false;
        }
        return true;
    }

    public boolean isDefined(String variable) {
        if (variables.containsKey(variable)) {
            return variables.get(variable).defined;
        }
        return false;
    }

    public void printTable(){}

    class VarInfo {
        Type type;
        boolean defined;

        public  VarInfo(Type type, boolean defined) {
            this.type = type;
            this.defined = defined;
        }

    }
}
