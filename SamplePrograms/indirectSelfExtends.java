class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Foo extends Baz{
    int a;
    int b;
}

class Baz extends Boo {
    int a;
    int b;
}

class Boo extends Foo {
    int a;
    int b;
}