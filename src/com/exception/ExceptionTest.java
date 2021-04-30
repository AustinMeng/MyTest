package com.exception;

public class ExceptionTest {

    void test1() {
        throw new RuntimeException("test1");
    }

    void test2() {
        try {
            test1();
        } catch (Exception e) {
            throw e;
        }
    }

    void test3() {
        try {
            test1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void test4() {
        try {
            test1();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        ExceptionTest exceptionTest = new ExceptionTest();
        try {
            exceptionTest.test2();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("...........................");
        }

        try {
            exceptionTest.test3();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("...........................");
        }

        try {
            exceptionTest.test4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
