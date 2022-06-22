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
                            .println("Waitperson " + name + " got " + restaurant.meal + " by " + Thread.currentThread());
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
                    if ((++restaurant.count) == 10) {
                        System.out.println("Out of food, closing");
                        restaurant.exec.shutdownNow();
                    }
                    System.out.println("Order up! " + restaurant.count + " by " + Thread.currentThread());
                    restaurant.meal = new Meal(restaurant.count);
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

    int count = 0;
    Meal meal;

    ExecutorService exec = Executors.newCachedThreadPool();
    public static void main(String[] args) throws InterruptedException {
        Restaurant1 restaurant = new Restaurant1();
        WaitPerson1 waitPerson = new WaitPerson1(restaurant, "0");
        Chef1 chef = new Chef1(restaurant);
        ExecutorService exec = restaurant.exec;
        //多线程单生产消费者
        exec.execute(chef);
        exec.execute(chef);
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(waitPerson);
        exec.execute(waitPerson);

        //多线程多消费生产者，效果一样，锁都是一个饭店
        TimeUnit.SECONDS.sleep(3);
        Restaurant1 restaurant1 = new Restaurant1();
        //restaurant1.exec = Executors.newFixedThreadPool(6);
        // 使用newCachedThreadPool，会导致有一个command得不到执行，SynchronousQueue每次只能放入一个command，
        // 而这里的command都是无限循环，已经启动的线程不会再去队列拿任务，由于SynchronousQueue被某一个任务占到，所以后面新来的都有直接启动新的线程
        exec = restaurant1.exec;
        exec.execute(new Chef1(restaurant1));
        exec.execute(new Chef1(restaurant1));
        exec.execute(new Chef1(restaurant1));
        exec.execute(new WaitPerson1(restaurant1, "austin"));
        exec.execute(new WaitPerson1(restaurant1, "john"));
        exec.execute(new WaitPerson1(restaurant1, "tylor"));
    }
} /* Output:

*///:~
