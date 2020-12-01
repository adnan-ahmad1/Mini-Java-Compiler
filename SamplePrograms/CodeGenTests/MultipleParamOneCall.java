class One {
    public static void main(String[] args){
        System.out.println(new Two().first(2, 3, 4, 5, 6));
    }
}

class Two {
    public int first(int a, int b, int c, int d, int e) {
        System.out.println(a);
        System.out.println(b+c);
        System.out.println(d+e);
        return 1;
    }
}

// 2
// 7
// 11
// 1