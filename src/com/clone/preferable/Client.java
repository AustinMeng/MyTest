package com.clone.preferable;

/**
 *
 You have two choices when designing a class for inheritance (Item 19),
 but whichever one you choose, the class should not implement Cloneable.
 You may choose to mimic the behavior of Object by implementing a properly functioning protected clone method that is declared to throw CloneNotSupportedException.
 This gives subclasses the freedom to implement Cloneable or not, just as if they extended Object directly. Like the implementation in this package.

 Alternatively, you may choose not to implement a working clone method, and to prevent subclasses from implementing one, by providing the following degenerate clone implementation:
 // clone method for extendable class not supporting Cloneable
 @Override
 protected final Object clone() throws CloneNotSupportedException {
 throw new CloneNotSupportedException();
 }
 */
public class Client {

    public static void main(String[] args) {
        //clone方法在哪个类对象上调用，就返回哪个类的对象的clone。比如a继承b，b继承c，在a对象上调用clone方法，返回a对象的clone，
        //当然可以向上转化为b或者c，b上调用clone方法产生的clone对象只能向上转化为c
        ConcretePrototype concretePrototype = new ConcretePrototype();
        concretePrototype.show();
        ConcretePrototype cpClone = (ConcretePrototype) concretePrototype.clone();
        cpClone.show();

        System.out.println("----------------------------------");
        Prototype prototype = new Prototype();
        Prototype prototypeClone = prototype.clone();
        prototype.show();;
        prototypeClone.show(); //of course, the current implementation won't generate a clone object from Prototype since super.clone will return null without implement Cloneable
    }

    /** the reference field won't be deep clone as default
     * output:
     * this:com.clone.ConcretePrototype@5cad8086 super:com.clone.ConcretePrototype@5cad8086
     * i:10 object:java.lang.Object@6e0be858
     * clone class Prototype
     * clone class ConcretePrototype
     * this:com.clone.ConcretePrototype@61bbe9ba super:com.clone.ConcretePrototype@61bbe9ba
     * i:30 object:java.lang.Object@6e0be858
     * ----------------------------------
     * clone class Prototype
     * this:com.clone.Prototype@610455d6 super:com.clone.Prototype@610455d6
     * i:10 object:java.lang.Object@511d50c0
     * this:com.clone.Prototype@60e53b93 super:com.clone.Prototype@60e53b93
     * i:10 object:java.lang.Object@511d50c0
     */
}
