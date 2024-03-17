package com;

@FunctionalInterface
public interface TestFunction<R,T> {
  R apply(T t);
  default void apply1(){
    System.out.println("apply1");
  }

  default void apply2() {
    System.out.println("apply2");
  }

}
