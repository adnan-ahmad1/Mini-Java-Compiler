class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Foo {
}

class Bar {
    Foo f;
    Dog d;

    public int m() {
        d = f.method();
        return 1;
    }
}