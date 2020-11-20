class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Animal {
    int age;

    public int getAge() {
        return age;
    }
}

class Wolf extends Animal {
    public boolean bark(){
        return false;
    }
}

class Dog extends Wolf {
    public boolean bark() {
        return true;
    }
}

class Cat extends Animal {
    public boolean meow() { return true; }
}

class Test {
    Animal a;
    Wolf w;
    Dog d;
    Cat c;

    public Animal getA() {
        a = new Dog();
        return a;
    }

    public Wolf getD() {
        d = new Dog();
        return d;
    }

    public Animal getD2() {
        d = new Dog();
        return d;
    }

    public Cat getC() {
        a = new Animal();
        return a;
    }

    public Cat getD3() {
        d = new Dog();
        return d;
    }

}

