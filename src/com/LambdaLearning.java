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
    int compare(LambdaLearning a,  Integer b);
}
@FunctionalInterface
interface MyFunctionalInterface4 {
     List compare(LambdaLearning a, Integer b);
}
public class LambdaLearning {
    int value;
    public static void main(String[] args) {
        MyFunctionalInterface myFunctionalInterface = () -> "Hello";
        System.out.println(myFunctionalInterface.sayHello());

        MyFunctionalInterface1 mfi = (x) -> x + 5;
        System.out.println(mfi.incrementByFive(4));

        //a lamda method with two parameters
        MyFunctionalInterface2 functionalInterface2 = (x, y)->x.compareTo(y);

        //an instance method in the class of first parameter with the format someMethod(secondParameter)
        MyFunctionalInterface2 functionalInterface2_1 = Integer::compareTo;
        MyFunctionalInterface3 functionalInterface3 = LambdaLearning::compare;//return type must be same
        System.out.println("func3 "+functionalInterface3.compare(new LambdaLearning(), 3));
        MyFunctionalInterface4 functionalInterface4 = LambdaLearning::compare1;//return type must be same
        System.out.println("func4 "+functionalInterface4.compare(new LambdaLearning(), 4));

        //An instance method fully match, must use instance :: method
        MyFunctionalInterface4 functionalInterface4_1 = new LambdaLearning()::compare2;

        //A static method fully match, can use class :: method
        MyFunctionalInterface4 functionalInterface4_2 = LambdaLearning::compare3;

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

    public int compare(Integer val) {
        return val;
    }

    public List compare1(Integer val) {
        return new ArrayList();
    }

    public List compare2(LambdaLearning a, Integer val) {
        return null;
    }

    public static List compare3(LambdaLearning a, Integer val) {
        return null;
    }


    interface MathOperation {
        int operation(int a, int b);
    }
}
