class One {
    public static void main(String[] args){
        System.out.println(new Two().first(2, 3));
    }
}

class Two {
    public int first(int a, int b) {
        System.out.println(a);
        System.out.println(b);
        return 1;
    }
}
