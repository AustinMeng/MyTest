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

@FunctionalInterface
interface MyFunctionalInterface2 {
    int compare(Integer a, Integer b);
}
@FunctionalInterface
interface MyFunctionalInterface3 {
    void compare(LambdaLearning a, Integer b);
}
@FunctionalInterface
interface MyFunctionalInterface4 {
    LambdaLearning compare(LambdaLearning a, Integer b);
}
public class LambdaLearning {
    public static void main(String[] args) {
        MyFunctionalInterface myFunctionalInterface = () -> "Hello";
        System.out.println(myFunctionalInterface.sayHello());

        MyFunctionalInterface1 mfi = (x) -> x + 5;
        System.out.println(mfi.incrementByFive(4));

        //provide a method with two parameters or a method from class of first parameter like the format firstParameter.someMethod(secondParameter)
        MyFunctionalInterface2 functionalInterface2 = (x, y)->x.compareTo(y);

        MyFunctionalInterface2 functionalInterface21 = Integer::compareTo;
        MyFunctionalInterface3 functionalInterface3 = LambdaLearning::compare;
        MyFunctionalInterface4 functionalInterface4 = LambdaLearning::compare1;
        //An instance method fully match or implement functional interface
        MyFunctionalInterface4 functionalInterface41 = new LambdaLearning()::compare2;

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

    public void compare(Integer val) {

    }

    public LambdaLearning compare1(Integer val) {
        return null;
    }

    public LambdaLearning compare2(LambdaLearning a, Integer val) {
        return null;
    }

    interface MathOperation {
        int operation(int a, int b);
    }
}
