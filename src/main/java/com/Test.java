package com;

import com.other.GrandpaClass;
import com.other.ParentClass;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {

  public static void main(String[] args) {

    Object o1 = new Object();
    Object o2 = o1;
    Object o3 = o2;
    System.out.println(o1 + " " + o2+ " " + o3);
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
      while ((n = in.read(buf)) >= 0) {
        out.write(buf, 0, n);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Integer a = 1000, b = 1000;
    Integer m = 100, n = 100;
    System.out.println(a == b);
    System.out.println(m == n);

    List<ParentClass> parentClassList = new ArrayList<>();
    List<ChildClass> childClassList = new ArrayList<>();
    //parentClassList = childClassList;

    List<? extends ParentClass> extendList = new ArrayList<>();
    List<? super ParentClass> superList = new ArrayList<>();

//    extendList.add(new ChildClass());//extendList cannot add any element, since the concrete type cannot be confirmed.
//    extendList.add(new ParentClass());
    superList.add(new ChildClass());
    superList.add(new ParentClass());

    //ChildClass childClass = extendList.get(0);
    ParentClass parentClass = extendList.get(0);
    //ParentClass parentClass1 = superList.get(0);//superList can not be read directly, since not which
    //GrandpaClass grandpaClass = superList.get(0);

    List<ChildClass> childClasses = new ArrayList<>();
    extendList = childClasses;
    //superList = childClasses;
    List<ParentClass> parentClasses = new ArrayList<>();
    extendList = parentClasses;
    superList = parentClasses;
    List<GrandpaClass> grandpaClasses = new ArrayList<>();
    //extendList = grandpaClasses;
    superList = grandpaClasses;

    Collections.synchronizedCollection(new ArrayList<>());


  }

}
