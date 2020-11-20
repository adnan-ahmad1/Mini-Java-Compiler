class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class extendsItself extends extendsItself {
    int a;
    int b;

    public int fooey(boolean baz, Foo c) {
        boolean baz;
        int a;
        a = baz;
        return a;
    }
}