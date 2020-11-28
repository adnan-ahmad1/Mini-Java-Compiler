class AndTest {
    public static void main(String[] args) {
        System.out.println(new Test().test());
    }
}

class Test {
    public int test() {
        if (true && true) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        if (true && false) {
            System.out.println(2);
        } else {
            System.out.println(3);
        }

        if (false && false) {
            System.out.println(4);
        } else {
            System.out.println(5);
        }

        if (true) {
            System.out.println(6);
        } else {
            System.out.println(7);
        }

        if (false) {
            System.out.println(8);
        } else {
            System.out.println(9);
        }

        return 100;
    }
}

// 1
// 3
// 5
// 6
// 9
// 100