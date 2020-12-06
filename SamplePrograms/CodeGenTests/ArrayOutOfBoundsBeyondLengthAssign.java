class One {
    public static void main(String[] args) {
        System.out.println(new Two().seventeen());
    }
}

class Two {
    int[] arr;

    public int seventeen() {

        arr = new int[100];

        arr[(0-50) * 3] = 1000;

        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println(arr[8]);
        System.out.println(arr[1000]);

        return 1;
    }
}