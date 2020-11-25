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
        return 17;
    }
}