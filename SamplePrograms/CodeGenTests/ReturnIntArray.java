class AndTest {
    public static void main(String[] args) {
        System.out.println((new Test().test())[0]);
    }
}

class Test {
    public int[] test() {
        int[] a;
        a = new int[new Test2().m1()];
        a[0] = 3;
        return a;
    }
}

class Test2 {
    public int m1() {
        return 5;
    }
}