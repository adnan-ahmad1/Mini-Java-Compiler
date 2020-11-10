package Semantics;

public class Unknown extends Type{
    private static Unknown unknown = new Unknown();
    private Unknown() {}
    public static Unknown getInstance() {
        return unknown;
    }
}
