package com.classInitialization;

import com.other.ClassOne;

public class ClassInitiateTest {
    static ClassOne one;
    static {
        System.out.println("ClassInitiateTest is loaded");
    }
    /*ClassOne one = getClassOne();
    ClassOne getClassOne() {
        return new ClassOne();
    }*/
    int m = getM();
    int getM() {
        System.out.println("instance variable is initiated");
        return 8;
    }
// If one object of Class is not created by new or the static method or static variable is not used, the Class won't be loaded
    public static void main(String[] args) {
//        ClassOne.staticMethodInvoke();
//        //System.out.println(ClassOne.v);
//
//        System.out.println(ClassInitiateTest.one);
//
//        System.out.println(new ClassInitiateTest().one);
    }
}

