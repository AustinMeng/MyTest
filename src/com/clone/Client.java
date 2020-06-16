package com.clone;

public class Client {

    public static void main(String[] args) {
        //clone方法在哪个类对象上调用，就返回哪个类的对象的clone。比如a继承b，b继承c，在a对象上调用clone方法，返回a对象的clone，
        //当然可以向上转化为b或者c，b上调用clone方法产生的clone对象只能向上转化为c
        ConcretePrototype concretePrototype = new ConcretePrototype();
        concretePrototype.show();
        ConcretePrototype cpClone = (ConcretePrototype) concretePrototype.clone();
        cpClone.show();

//        Prototype prototype = new Prototype();
//        Prototype prototypeClone = prototype.clone();
//        ConcretePrototype cpClone1 = (ConcretePrototype) prototypeClone;
    }
}
