package com.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Test {

  private long count;
  private void add10K() {
    int idx = 0;
    while(idx++ < 10000) {
      count ++;
    }
  }

  public static long calc() throws InterruptedException {
    Test test = new Test();
    Thread th1 = new Thread(() -> {test.add10K();});
    Thread th2 = new Thread(() -> {test.add10K();});
    th1.start();
    th2.start();

    th1.join();
    th2.join();
    return test.count;
  }
  public static void main(String[] args) throws InterruptedException {
    System.out.println(calc());
    CountDownLatch countDownLatch = new CountDownLatch(0);
    CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

  }

}
