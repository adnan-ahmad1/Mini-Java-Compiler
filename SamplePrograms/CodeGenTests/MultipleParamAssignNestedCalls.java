class One {
    public static void main(String[] args){
        System.out.println(new Two().first(10, 20, 30, 40));
    }
}

class Two {
    public int first(int a, int b, int c, int d) {
        int e;
        int f;
        int g;
        Three three;
        Five five;
        Four four;

        three = new Three();
        /*
        five = new Five();
        four = five.getFour();

         */

        e = a;
        f = b;
        g = c;


        //System.out.println(new Four().mix(three.t(e, f, g, d), 20, four.getNum()));


        // apparently you cant do CHECK WITH JOSH
        System.out.println(new Four().mix(three.t(e, f, g, d), 20, (new Five()).getFour().getNum()));
        return 1;
    }
}

class Three {
    int n;

    public int t(int a, int b, int c, int d) {
        System.out.println(a - b - c - d);
        return a - b - c - d;
    }

}

class Four {
    int v1;
    int v2;
    int v3;

    public int mix(int a, int b, int c) {
        System.out.println((a + b) * c);
        return 0;
    }

    public int getNum() {
        return 10;
    }

}

class Five {
    public Four getFour() {
        return new Four();
    }
}

// expected:
// -80
// -600
// 0
// 1

