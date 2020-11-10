package Semantics;

public class Void extends Type{
    private static Void voidType = new Void();
    private Void() {}
    public static Void getInstance() {
        return voidType;
    }
}
