class Test {
    public static void main(String[] args) {
        System.out.println(new T().t());
    }
}

class T {
    public int t() {
        Dog d;

        d = new Dog();

        System.out.println(d.bark());

        System.out.println(d.getShadowNum());
        System.out.println(d.setNum(50));

        System.out.println(d.getNum());
        System.out.println(d.getShadowNum());

        return 1;
    }
}


class Animal {
    int num;

    public int bark() {
        return 0;
    }

    public int getNum() {
        return num;
    }

    public int setNum(int n) {
        num = n;
        return 100;
    }
}

class Dog extends Animal{
    int num;

    public int bark() {
        num = 10;
        return 1;
    }

    public int getShadowNum() {
        return num;
    }

}

// 1
// 10
// 100
// 50
// 10
// 1