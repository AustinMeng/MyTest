package com;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface MyFunctionalInterface {
    String sayHello();

}

@FunctionalInterface
interface MyFunctionalInterface1 {
    int incrementByFive(int a);
}

public class LambdaLearning {
    public static void main(String[] args) {
        MyFunctionalInterface myFunctionalInterface = () -> "Hello";
        System.out.println(myFunctionalInterface.sayHello());

        MyFunctionalInterface1 mfi = (x) -> x + 5;
        System.out.println(mfi.incrementByFive(4));

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.forEach((name) -> System.out.println(name));

        MathOperation add = (a, b) -> a + b;
        System.out.println(add.operation(4, 5));

        MathOperation subtraction = (a, b) -> a - b;
        System.out.println(subtraction.operation(5, 4));

    }

    interface MathOperation {
        int operation(int a, int b);
    }
}
