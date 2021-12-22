package com.concurrent;
//: concurrency/Restaurant.java
// The producer-consumer approach to task cooperation.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class WaitPerson1 implements Runnable {

    private Restaurant1 restaurant;
    private String name;

    public WaitPerson1(Restaurant1 r, String name) {
        restaurant = r;
        this.name = name;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (restaurant) {
                    while (restaurant.meal == null) {
                        restaurant.wait(); // ... for the chef to produce a meal
                    }
                    System.out
                            .println("Waitperson" + name + " got " + restaurant.meal + " by " + Thread.currentThread());
                    restaurant.meal = null;
                    restaurant.notifyAll(); // Ready for another
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class Chef1 implements Runnable {

    private Restaurant1 restaurant;
    private int count = 0;

    public Chef1(Restaurant1 r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (restaurant) {
                    while (restaurant.meal != null) {
                        restaurant.wait(); // ... for the meal to be taken
                    }
                    if ((++count) == 10) {
                        System.out.println("Out of food, closing");
                        restaurant.exec.shutdownNow();
                    }
                    System.out.println("Order up! " + count + " by" + Thread.currentThread());
                    restaurant.meal = new Meal(count);
                    restaurant.notifyAll();
                    TimeUnit.MILLISECONDS.sleep(100);
                }

            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

/**
 * 多producer和consumer版本,使用reentrantLock condition会更优雅。
 */
public class Restaurant1 {

    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson1 waitPerson = new WaitPerson1(this, "0");
    Chef1 chef = new Chef1(this);

    public Restaurant1() {
        exec.execute(chef);
        exec.execute(chef);
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(waitPerson);
        exec.execute(waitPerson);

    }

    public static void main(String[] args) {
        new Restaurant1();
    }
} /* Output:
Order up! Waitperson got Meal 1
Order up! Waitperson got Meal 2
Order up! Waitperson got Meal 3
Order up! Waitperson got Meal 4
Order up! Waitperson got Meal 5
Order up! Waitperson got Meal 6
Order up! Waitperson got Meal 7
Order up! Waitperson got Meal 8
Order up! Waitperson got Meal 9
Out of food, closing
WaitPerson interrupted
Order up! Chef interrupted
*///:~
