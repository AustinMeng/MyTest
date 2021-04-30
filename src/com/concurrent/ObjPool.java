package com.concurrent;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class ObjPool<T, R> {
  final List<T> pool;
  final Semaphore sem;
  ObjPool(int size, T t) {
    pool = new Vector<T>();
    for (int i = 0; i<size; i++) {
      pool.add(t);
    }
    sem = new Semaphore(size);
  }

  R exec(Function<T, R> func) throws InterruptedException {
    T t = null;
    sem.acquire();
    try{
      t = pool.remove(0);
      return func.apply(t);
    } finally {
      pool.add(t);
      sem.release();
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ObjPool<Long, String> pool = new ObjPool<Long, String>(10, (long) 2);
    pool.exec(t->{
      System.out.println(t);
      return t.toString();
    });
  }

}
