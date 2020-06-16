package com.clone;

public class ConcretePrototype extends Prototype {

  public void show() {
    System.out.println("this:" + this.toString() + " super:" + super.toString());
  }

  public ConcretePrototype clone() {
    ConcretePrototype protoType = null;
    protoType = (ConcretePrototype) super.clone();
    protoType.i = 30;
    System.out.println("clone class ConcretePrototype");
    return protoType;
  }
}
