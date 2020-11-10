package Semantics;

public class BooleanType extends Type{
    public static BooleanType bool = new BooleanType();

    private BooleanType() { }

    public static BooleanType getInstance() {
        return bool;
    }
}
