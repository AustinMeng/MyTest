package com.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class ProcedureOfMakingTea {


  public static void main(String[] args) throws ExecutionException, InterruptedException {
    FutureTask<String> ft2 = new FutureTask<>(new T2Task());

    FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));

    Thread t1 = new Thread(ft1);
    Thread t2 = new Thread(ft2);
    t1.start();
    t2.start();
    System.out.println(ft1.get());
  }

}

class T1Task implements Callable<String> {

  FutureTask<String> ft2;

  T1Task(FutureTask<String> ft) {
    ft2 = ft;
  }


  @Override
  public String call() throws Exception {
    System.out.println("T1: clean water pot...");
    TimeUnit.SECONDS.sleep(1);
    System.out.println("T1: boil water...");
    TimeUnit.SECONDS.sleep(10);
    String tf = ft2.get();
    System.out.println("T1: get tea " + tf);
    return "tea is ready";
  }
}

class T2Task implements Callable<String> {

  @Override
  public String call() throws Exception {
    System.out.println("T2: clean tea pot...");
    TimeUnit.SECONDS.sleep(1);
    System.out.println("T2: boil tea cup...");
    TimeUnit.SECONDS.sleep(2);
    System.out.println("T2: take tea");
    TimeUnit.SECONDS.sleep(1);
    return "green tea";
  }
}
