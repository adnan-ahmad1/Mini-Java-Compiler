class Test {
    public static void main(String[] args) {
        System.out.println(new T().t());
    }
}

class T {
    public int t() {
        A a;
        B b;

        a = new A();
        b = new B();

        System.out.println(a.m());

        if (b.m()) {
            System.out.println(500);
        } else {
            System.out.println(9999);
        }

        a = b;

        if (a.m()) {
            System.out.println(900);
        } else {
            System.out.println(9999);
        }

        return 0;
    }
}

class A {
    public Animal m() {
        return new Animal();
    }
}

class B extends A{
    public Dog m() {
        return new Dog();
    }
}

class Animal {
    public boolean bark() {
        return false;
    }

}

class Dog {
    public boolean bark() {
        return true;
    }
}

// 1
// 500
// 900
// 0