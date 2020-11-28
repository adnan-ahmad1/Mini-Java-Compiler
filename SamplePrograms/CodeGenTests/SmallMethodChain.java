class One {
    public static void main(String[] args){
        System.out.println(new Two().first().getNum()); // 1
    }
}

class Two {
    public Three first() {
        Three three;
        three = new Three();
        return three;
    }
}

class Three {
    public int getNum() {
        return 1;
    }
}