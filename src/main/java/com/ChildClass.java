package com;

import com.other.ParentClass;

public class ChildClass extends ParentClass {
//    @Override
//    protected void test2() {
//        System.out.println("ChildClass protected test2");
//    }

    public static void main(String[] args) {
        //new ChildClass().test1();
        new ChildClass().test2();
        new ChildClass().test3();
    }
}
