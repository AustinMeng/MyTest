package com.clone;

public class Client {

    public static void main(String[] args) {
        ConcretePrototype concretePrototype = new ConcretePrototype();
        ConcretePrototype cpClone = (ConcretePrototype) concretePrototype.clone();
        cpClone.show();

//        Prototype prototype = new Prototype();
//        Prototype prototypeClone = prototype.clone();
//        ConcretePrototype cpClone1 = (ConcretePrototype) prototypeClone;
    }
}
