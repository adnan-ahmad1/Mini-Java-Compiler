class One {
    public static void main(String[] args){
        System.out.println(new Test().test());
    }
}

class Test{

    public int test() {
        int num;
        A a;
        a = new A();

        System.out.println(a.getA().getNum());
        num = this.print();
        return 1000;
    }

    public int print() {
        System.out.println(500);
        return 1;
    }
}

class A {
    int n1;

    public A getA() {
        n1 = 2;
        return this;
    }

    public int getNum() {
        return n1;
    }
}

// 2
// 500
// 1000