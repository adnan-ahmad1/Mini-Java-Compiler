class One {
    public static void main(String[] args){
        System.out.println(new Two().first());
    }
}

class Two {
    boolean a;

    public int first() {
        boolean b;
        boolean c;

        a = true;
        b = false;

        if (a && b) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }

        if (!(a && b)) {
            System.out.println(5);
        } else {
            System.out.println(6);
        }

        if (!b && a) {
            System.out.println(7);
        } else {
            System.out.println(8);
        }

        c = a && b;

        if (c) {
            System.out.println(3);
        } else {
            System.out.println(4);
        }

        return 5;
    }
}

