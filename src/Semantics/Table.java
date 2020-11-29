package Semantics;

import java.util.*;

public class Table {
    Map<String, Type>  variables;

    public Table() {
        variables = new HashMap<>();
    }

    public boolean addVariable(String variable, Type type){
        if (variables.containsKey(variable)) {
            return false;
        }
        variables.put(variable, type);
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
            return variables.get(variable);
        }
        return null;
    }

    public List<String> getVariableNames() {
        List<String> variablesList = new ArrayList<>();
        for (String s : variables.keySet()) {
            variablesList.add(s);
        }
        return variablesList;
    }

    public void printTable(){}

}
