package Semantics;

public class IntegerType implements Type{
    private static IntegerType integer = new IntegerType();
    private IntegerType() {}

    public static IntegerType getInstance() {
        return integer;
    }
    public boolean equals(Type type) {
        return type == getInstance();
    }

    public String toString() {
        return "int";
    }
}
