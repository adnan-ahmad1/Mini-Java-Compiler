package Semantics;

public class StringType extends Type{
    private static StringType string = new StringType();
    private StringType() {}
    public static StringType getInstance() {
        return string;
    }
}
