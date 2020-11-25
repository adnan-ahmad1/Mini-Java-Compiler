class One {
    public static void main(String[] args) {
        System.out.println(new Two().seventeen());
    }
}

class Two {
    public int add() {
        return 1 + 1;
    }

    public int seventeen() {
        System.out.println(1);
        System.out.println(3 * new Two().add());
        System.out.println(9 * (3 - 1) + 5);
        return 17;
    }
}