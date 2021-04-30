package com.concurrent;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


public class CompletableTest {

  public static void main(String[] args) {
    CompletableFuture<String> f1 = CompletableFuture.supplyAsync(
        ()-> {
          int t = new Random().nextInt(5);
          try {
            TimeUnit.SECONDS.sleep(t);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return String.valueOf("f1"+t);
        }
    );

    CompletableFuture<String> f2 = CompletableFuture.supplyAsync(
        ()-> {
          int t = new Random().nextInt(5);
          try {
            TimeUnit.SECONDS.sleep(t);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return String.valueOf("f2"+t);
        }
    );

    CompletableFuture<String> f3 = f1.applyToEither(f2, s->s);

    System.out.println(f3.join());
  }

}
