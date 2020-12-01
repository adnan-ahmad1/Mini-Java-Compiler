class Test {
    public static void main(String[] args) {
        System.out.println(new Test2().t());
    }
}

class Test2 {
    public int t() {
        int d1;
        int d2;
        int d3;
        int d4;

        Two two;
        One one;

        two = new Two();
        one = two;

        d1 = one.setTag();
        System.out.println(one.getTag());
        d2 = one.setIt(17);
        d3 = two.setTag();
        System.out.println(two.getIt());
        System.out.println(two.getThat());
        d4 = one.setIt(42);
        System.out.println(two.getIt());
        System.out.println(two.getThat());

        return 0;
    }

    // expected:
    // 2
    // 17
    // 3
    // 42
    // 3
    // 0
}

class One {
    int tag;
    int it;

    public int setTag() {
        tag = 1;
        return 1;
    }

    public int getTag() {
        return tag;
    }

    public int setIt(int num) {
        it = num;
        return 1;
    }

    public int getIt() {
        return it;
    }
}

class Two extends One {
    int it;

    public int setTag() {
        tag = 2;
        it = 3;
        return 1;
    }

    public int getThat() {
        return it;
    }
}