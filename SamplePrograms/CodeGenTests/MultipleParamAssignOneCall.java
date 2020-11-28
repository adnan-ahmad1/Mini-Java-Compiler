class One {
    public static void main(String[] args){
        System.out.println(new Two().first(1, 2, 3, 4));
    }
}

class Two {
    public int first(int a, int b, int c, int d) {
        int e;
        int f;

        e = 5;
        f = 6;

        System.out.println(e + f); // 11
        System.out.println(a + b + c + d); // 10
        System.out.println(a + b + c + d + e + f); // 21
        System.out.println(e + 1 + a + 1); // 8

        return f; // 6
    }
}