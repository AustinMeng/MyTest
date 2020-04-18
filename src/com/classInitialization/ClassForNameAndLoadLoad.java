package com.classInitialization;

import com.other.ClassOne;

import java.lang.reflect.Field;

public class ClassForNameAndLoadLoad {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //with initialization
        //Class classOneClass = Class.forName("com.other.ClassOne");
        //without initialization
        //Class.forName("com.other.ClassOne", false, ClassForNameAndLoadLoad.class.getClassLoader());
        //without initialization
        Class classOneClass = ClassForNameAndLoadLoad.class.getClassLoader().loadClass("com.other.ClassOne");
        Field field = classOneClass.getDeclaredField("v");
       // field.setAccessible(true);

        //Although loadClass won't initialize the Class(run the static block, assign static variable),
        //but if the static variable is used or gotten, the Class will be initialized.
        //System.out.println(field.getInt(classOneClass));

    }
}
