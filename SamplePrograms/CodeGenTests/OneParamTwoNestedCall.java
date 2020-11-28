class One {
    public static void main(String[] args){
        System.out.println(new Two().first(2, new Three().getNum(), new Four().getNum(5)));
    }
}

class Two {
    public int first(int a, int b, int c) {
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        return 1;
    }
}

class Three {
    public int getNum() {
        return 3;
    }
}

class Four {
    public int getNum(int b) {
        return 4 + b;
    }
}

// should return:
// 2
// 3
// 9
// 1