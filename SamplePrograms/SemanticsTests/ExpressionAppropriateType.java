class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class T {
    int a;
    int b;

    public int calculate() {
        if (1) a = 3;
        else a = 1;

        while (true && false) b = 10 + a;

        if (a < b) b = 2;
        else b = 1 + true;

        while (1 && true) b = 10;

        return 1;
    }

}