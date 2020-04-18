package com.other;

public class ClassOne {
    public static int v = 8;
    static {
        System.out.println("classone is loaded with v = "+v);
    }

    int j = getJ();

    int getJ() {
        System.out.println("instance getJ() was invoked");
        return 5;
    }
    public static void staticMethodInvoke() {
        System.out.println("static method invoke");
    }
}
