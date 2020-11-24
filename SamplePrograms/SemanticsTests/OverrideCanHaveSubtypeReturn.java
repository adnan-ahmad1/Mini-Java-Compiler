class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Animal {
    public Animal get(){
        Animal a;
        a = new Animal();
        return a;
    }
}

class Dog extends Animal{
    public Dog get(){
        Dog d;
        d = new Dog();
        return d;
    }
}

