package Semantics;

public class IntArrayType extends Type{
    private static IntArrayType intArray = new IntArrayType();
    private IntArrayType() {}

    public static IntArrayType getInstance() {
        return intArray;
    }
}
