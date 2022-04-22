package com;

import com.sun.org.apache.xpath.internal.operations.String;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Test {

    public static void main(String[] args) {
//    Map<String, String> map = new LinkedHashMap<>();
//
//    map.put("a", "1");
//    map.put("b", "2");
//    map.put("c", "3");
//    map.forEach((x, y) -> System.out.print(x));
//    System.out.println(".................");
//
//    map.get("a");
//    map.forEach((x, y) -> System.out.print(x));
//    System.out.println(".................");
//
//    Map<String, String> lruMap = new LinkedHashMap<>(16,0.75F, true);
//
//    lruMap.put("a", "1");
//    lruMap.put("b", "2");
//    lruMap.put("c", "3");
//    lruMap.forEach((x, y) -> System.out.print(x));
//    System.out.println(".................");
//
//    lruMap.get("a");
//    lruMap.forEach((x, y) -> System.out.print(x));
//    System.out.println(".................");
//
//
//    String str1 = new String("test");
//    String str2 = "test";
//    System.out.println(str1 == str2);
//    String str = str1.intern();
//    System.out.println(str == str2);
//
//    LocalDateTime now = LocalDateTime.now(ZoneId.of("Australia/Darwin"));
//    System.out.println(now);

        try (BufferedReader br = new BufferedReader(
                new FileReader("path"))) {
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getSuppressed().length);
            //Arrays.stream(e.getSuppressed()).forEach(x-> System.out.println(x.getMessage()));
        }

        try (InputStream in = new FileInputStream("src");
                OutputStream out = new FileOutputStream("dst")) {
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer a = 1000, b=1000;
        Integer m = 100, n = 100;
        System.out.println(a == b);
        System.out.println(m == n);

    }
}
