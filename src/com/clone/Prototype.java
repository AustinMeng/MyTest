package com.clone;


public class Prototype implements Cloneable {
    public int i = 10;

    public Prototype clone() {
        Prototype protoType = null;
        try {
            protoType = (Prototype) super.clone();
            protoType.i = 50;
            System.out.println("clone class Prototype");
        } catch (CloneNotSupportedException e) {

        }
        return protoType;
    }

    //
//    public ConcretePrototype clone() {
//        ConcretePrototype protoType = null;
//        try {
//            //如果在ConcretePrototype的对象上调用clone，这里转化为子类不抛错
              //说明object clone返回调用对象的复制对象，所以可以转化为父类
//            protoType = (ConcretePrototype) super.clone();
//            protoType.i = 50;
//        } catch (CloneNotSupportedException e) {
//
//        }
//        return protoType;
//    }
}
