package Semantics;

public class BooleanType implements Type{
    public static BooleanType bool = new BooleanType();

    private BooleanType() { }

    public static BooleanType getInstance() {
        return bool;
    }

    public boolean equals(Type type) {
        return type == getInstance();
    }

    public String toString() {
        return "boolean";
    }
}
