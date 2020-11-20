class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Dog {
    int age;

    public boolean bark() {
        return true;
    }
}

class Zoo {
    Dog a;
    Dog b;

    public boolean play() {
        boolean c;
        boolean d;

        a = new Dog();
        b = new Dog();

        c = a.bark();
        d = b.meow();

        return false;
    }
}