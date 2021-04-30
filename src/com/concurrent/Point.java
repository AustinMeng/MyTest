package com.concurrent;

import java.util.concurrent.locks.StampedLock;

public class Point {
  private int x, y;
  final StampedLock sl = new StampedLock();
  int distanceFromOrigin() {
    long stamp = sl.tryOptimisticRead();
    int curX = x, curY =y;

    if(!sl.validate(stamp)) {
      stamp = sl.readLock();
      try{
        curX = x;
        curY = y;
      } finally {
        sl.unlockRead(stamp);
      }
    }
    return (int) Math.sqrt(curX*curX + curY*curY);
  }

}
