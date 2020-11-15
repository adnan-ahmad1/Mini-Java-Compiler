package Semantics;

public class Unknown implements Type{
    private static Unknown unknown = new Unknown();
    private Unknown() {}
    public static Unknown getInstance() {
        return unknown;
    }

    public boolean equals(Type type) {
        return type == getInstance();
    }

    public String toString() {
        return "unknown";
    }
}
