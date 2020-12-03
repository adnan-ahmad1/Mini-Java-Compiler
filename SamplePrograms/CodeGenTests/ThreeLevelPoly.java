class Test {
    public static void main(String[] args){
        System.out.println(new Run().run());
    }
}

class Run {
    One one;
    Two two;
    Three three;
    public int run() {
        one = new Two();
        two = new Two();
        three = new Three();

        System.out.println(one.m1());

        System.out.println(two.m1());
        System.out.println(two.m2());

        System.out.println(three.m1());
        System.out.println(three.m2());
        System.out.println(three.getN());

        return 999;
    }
}

class One {
  public int m1() {
      return 11;
  }
}

class Two extends One {
    int n;

    public int m1() {
        return 12;
    }

    public int m2() {
        return 22;
    }

}

class Three extends Two {
    public int getN() {
        n = 3;
        return n;
    }
}