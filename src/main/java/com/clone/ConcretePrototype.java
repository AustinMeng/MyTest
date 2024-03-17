package com.clone;

public class ConcretePrototype extends Prototype {

  public void show() {
    System.out.println("this:" + this.toString() + " super:" + super.toString());
    System.out.println("i:" + this.i + " object:" + this.object); //
  }

  public ConcretePrototype clone() {
    ConcretePrototype protoType = null;
    protoType = (ConcretePrototype) super.clone();
    protoType.i = 30;
    System.out.println("clone class ConcretePrototype");
    return protoType;
  }
}
