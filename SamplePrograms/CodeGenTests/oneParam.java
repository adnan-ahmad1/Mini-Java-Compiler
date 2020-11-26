class One {
    public static void main(String[] args){
        System.out.println(new Two().first(3));
    }
}

class Two {
    public int first(int a) {
        System.out.println(a);
        return 1;
    }
}

// first one parameter, one method call
// next multiple parameters, one method call
// next multiple parameters, assignments, one method call
// next one parameter, two nested method calls
// next multiple parameters, two nested method calls
// last multiple parameters, multiple nexted method calls