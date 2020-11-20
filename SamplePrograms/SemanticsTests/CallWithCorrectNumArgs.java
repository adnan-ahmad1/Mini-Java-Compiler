class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Dog {
    public int bigSum(int n1, int n2, int n3, int n4) {
        return n1 + n2 + n3 + n4;
    }
}

class Test {

    public int testBigSum() {
        Dog dog;
        int t1;
        int t2;
        int t3;
        int t4;
        int t5;
        int[] arr;

        dog = new Dog();

        t1 = dog.bigSum(1, 2, 3, 4, 5);
        t2 = dog.bigSum(1, true, 3, false);
        t3 = dog.bigSum(1, 2, 3, arr);
        t4 = dog.bigSum(1, 2, 3);
        t5 = dog.bigSum(1, 2, 3, 4);

        return 1;
    }
}