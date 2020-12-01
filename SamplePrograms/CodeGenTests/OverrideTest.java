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

        if (a.m().bark()) {
            System.out.println(1);
        } else {
            System.out.println(2);
        }


        if (b.m().bark()) {
            System.out.println(3);
        } else {
            System.out.println(4);
        }

        a = b;

        if (a.m().bark()) {
            System.out.println(5);
        } else {
            System.out.println(6);
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

class Dog extends Animal{
    public boolean bark() {
        return true;
    }
}

// 2
// 3
// 5
// 0