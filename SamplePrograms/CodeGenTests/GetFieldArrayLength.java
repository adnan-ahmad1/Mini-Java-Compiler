class One {
    public static void main(String[] args){
        System.out.println(new Two().first());
    }
}

class Two {
    int[] a;

    public int first() {
        a = new int[5];

        a[0] = 1;
        a[1] = 2;
        a[3] = 4;

        System.out.println(a[0]);
        System.out.println(a[1] + a[3]);
        return a.length;
    }
}

// 1
// 6
// 5