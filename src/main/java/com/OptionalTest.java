package com;

import java.util.Optional;

public class OptionalTest {
    private String str;
    public OptionalTest(String str) {
        this.str = str;
    }
    public static void main(String[] args) {
        OptionalTest optionalTest1 = new OptionalTest("a");
        System.out.println(Optional.ofNullable(optionalTest1).orElse(new OptionalTest("b")).str);

        optionalTest1 = null;
        System.out.println(Optional.ofNullable(optionalTest1).orElse(new OptionalTest("b")).str);
    }

}
