package CodeGen;

import Semantics.ClassSemanticTable;
import Semantics.SemanticTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VTableAndVariableCalculation {

    private Map<String, List<String>> fieldOffsets;

    public VTableAndVariableCalculation() {
        fieldOffsets = new HashMap<>();
    }

    public void calculateFieldOffsets(SemanticTable st, String className) {
        List<String> variables = new ArrayList<>();
        ClassSemanticTable cst = st.getClass(className);

        helper(cst, variables);

        fieldOffsets.put(className, variables);
    }

    public void helper(ClassSemanticTable cst, List<String> variables) {
        if (cst.getSuperClass() != null) {
            helper(cst.getSuperClass(), variables);
        }

        for (String varName : cst.getVariableNames()) {
            variables.add(varName);
        }
    }
}
