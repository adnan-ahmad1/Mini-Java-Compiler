// should stack align on calls, new arrays, method decl
class One {
    public static void main(String[] args){
        System.out.println(new Two().first(3, 4, 5));
    }
}

class Two {
    public int first(int a, int b, int c) {
        int t;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        t = new Three().m1(c);
        System.out.println(t);

        return 1;
    }
}

class Three {
    public int m1(int a) {
        int[] b;

        b = new int[new Four().m(a, 2)];

        b[0] = 0;
        b[1] = 1;
        b[2] = 2;
        System.out.println(b[0]);
        System.out.println(b[1]);
        System.out.println(b[2]);

        return 0 - 10;
    }
}

class Four {
    public int m(int a, int b) {
        return a;
    }
}

// 3
// 4
// 5
// 0
// 1
// 2
// -10
// 1