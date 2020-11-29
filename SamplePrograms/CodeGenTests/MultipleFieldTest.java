class One {
    public static void main(String[] args){
        System.out.println(new Two().first(100));
    }
}

class Two {
    int n1;
    int n2;
    int n3;

    public int first(int a) {
        n1 = 1;
        n2 = 2;
        n3 = n2;

        System.out.println(n1);
        System.out.println(n2);
        System.out.println(n3);

        n1 = a;
        System.out.println(n1);

        return 5;
    }
}

// 1
// 2
// 2
// 100
// 5