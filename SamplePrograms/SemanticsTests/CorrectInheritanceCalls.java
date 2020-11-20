class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Animal {
    int age;

    public boolean live() {
        return true;
    }

    public int getAge() {
        return age;
    }
}

class Dog extends Animal{

    public boolean bark() {
        return true;
    }
}

class Test {
    public int test() {
        boolean b;
        Dog d;
        int n;

        d = new Dog();
        b = d.live();
        n = d.getAge();
        b = d.bark();

        return n;
    }
}