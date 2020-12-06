class One {
    public static void main(String[] args) {
        System.out.println(new Two().seventeen());
    }
}

class Two {

    public int seventeen() {
        int[] a;

        a = new int[25];

        a[20] = 20;
        a[24] = 24;
        a[0] = 0;

        System.out.println(a[0]);
        System.out.println(a[20]);
        System.out.println(a[24]);
        System.out.println(a[0-5]);

        return 1;
    }
}