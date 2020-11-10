package Semantics;

public class IntegerType extends Type{
    private static IntegerType integer = new IntegerType();
    private IntegerType() {}

    public static IntegerType getInstance() {
        return integer;
    }
}
