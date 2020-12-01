class LessThan {
    public static void main(String[] args) {
        System.out.println(new Test().t());
    }
}

class Test {
    public int t() {
        if (!true) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        if (!false) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        return 999;
    }
}

// 0
// 1
// 999