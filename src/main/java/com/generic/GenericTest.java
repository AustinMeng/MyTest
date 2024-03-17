package com.generic;

public class GenericTest<T> {

  public <E> E test1(E e) {
    return e;
  }

  public T test(T t) {
    return t;
  }

}
