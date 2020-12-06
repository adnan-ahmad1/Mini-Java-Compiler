class One {
    public static void main(String[] args) {
        System.out.println(new Two().seventeen());
    }
}

class Two {

    public int seventeen() {
        int[] a;

        a = new int[3];

        a[0] = 0;
        a[1] = 1;
        a[2] = 2;

        System.out.println(a[0]);
        System.out.println(a[1]);
        System.out.println(a[2]);
        System.out.println(a[3]);

        return 1;
    }
}