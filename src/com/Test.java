package com;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Test {

  public static void main(String[] args) {
    Map<String, String> map = new LinkedHashMap<>();

    map.put("a", "1");
    map.put("b", "2");
    map.put("c", "3");
    map.forEach((x, y) -> System.out.print(x));
    System.out.println(".................");

    map.get("a");
    map.forEach((x, y) -> System.out.print(x));
    System.out.println(".................");

    Map<String, String> lruMap = new LinkedHashMap<>(16,0.75F, true);

    lruMap.put("a", "1");
    lruMap.put("b", "2");
    lruMap.put("c", "3");
    lruMap.forEach((x, y) -> System.out.print(x));
    System.out.println(".................");

    lruMap.get("a");
    lruMap.forEach((x, y) -> System.out.print(x));
    System.out.println(".................");


    String str1 = new String("test");
    String str2 = "test";
    System.out.println(str1 == str2);
    String str = str1.intern();
    System.out.println(str == str2);

    LocalDateTime now = LocalDateTime.now(ZoneId.of("Australia/Darwin"));
    System.out.println(now);
  }
}
