class One {
    public static void main(String[] args){
        System.out.println(new Two().first(100));
    }
}

class Two {
    int num;

    public int first(int a) {
        num = a;
        System.out.println(num);
        return 1;
    }
}

// 100
// 1