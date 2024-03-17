package com;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    private AtomicLong id = new AtomicLong(0);
    private final IdGenerator instance;

    private IdGenerator() {
        instance = new IdGenerator();
    }

//    public static IdGenerator getInstance() {
//        if (instance == null) {
//            synchronized (IdGenerator.class) { // 此处为类级别的锁
//                if (instance == null) {
//                    instance = new IdGenerator();
//                }
//            }
//        }
//        return instance;
//    }

    public long getId() {
        return id.incrementAndGet();
    }

}
