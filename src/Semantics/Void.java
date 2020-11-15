package Semantics;

public class Void implements Type{
    private static Void voidType = new Void();
    private Void() {}
    public static Void getInstance() {
        return voidType;
    }

    public boolean equals(Type type) {
        return type == getInstance();
    }

    public String toString() {
        return "void";
    }
}
