package Semantics;

import AST.Identifier;

import java.util.ArrayList;
import java.util.List;

public class IdentifierType extends Type {
    String type;
    IdentifierType superclass;
    public IdentifierType(String type) {
        this.type = type;
    }
}