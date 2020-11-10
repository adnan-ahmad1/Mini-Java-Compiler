package Semantics;

import java.util.Map;

public class ClassSemanticTable {
    private Map<String, VarSemanticTable> variables;
    private Map<String, MethodSemanticTable> methods;
    private String className;

    void addMethod(String method) {}
    void addVariable(String variable){}
    VarSemanticTable getVarInfo(String variable){return null;}
    MethodSemanticTable getMethodInfo(String method){return null;}
    String getName(){return className;}
}
