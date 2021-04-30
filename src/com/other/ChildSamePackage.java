package com.other;

public class ChildSamePackage {
    ParentClass parentClass = new ParentClass();

    public static void main(String[] args) {
        ChildSamePackage childSamePackage = new ChildSamePackage();
        childSamePackage.parentClass.test1();
        childSamePackage.parentClass.test2();
        childSamePackage.parentClass.test3();
    }
}
