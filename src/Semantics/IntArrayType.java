package Semantics;

public class IntArrayType implements Type{
    private static IntArrayType intArray = new IntArrayType();
    private IntArrayType() {}

    public static IntArrayType getInstance() {
        return intArray;
    }

    public boolean equals(Type type) {
        return type == getInstance();
    }

    public String toString() {
        return "int[]";
    }
}
