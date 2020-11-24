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

    Baz b;

    public int m1() {
        d = f.method();
        return 1;
    }

    public int m2() {
        d = b;
        return 1;
    }

    public int m3() {
        Lion l;
        Tiger t;

        l = t;
        return 1;
    }

    public int m4() {
        Lion l;
        l = b;
        return 1;
    }
}