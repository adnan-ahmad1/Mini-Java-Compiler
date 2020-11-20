class Empty {
    public static void main(String[] args) {
        System.out.println(1);
    }
}

class Animal {
    public int m(int a, boolean b, int[] c) {
        return 1;
    }
}

class Wolf extends Animal {
}

class RegularDog extends Wolf {
    public boolean m(boolean a, int b, int[] c) {
        return true;
    }
}

class AlienDog extends Wolf {
    public boolean m(int a, boolean b) {
        return true;
    }
}

class MoonDog extends Wolf {
    public int m(int a, boolean b, int[] c) {
        return 0;
    }
}