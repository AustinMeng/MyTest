package com;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByExample {
  public static void main(String[] args) {
    // 假设有一组对象列表
    List<MyClass> myList = Arrays.asList(
        new MyClass("A", "X", 10),
        new MyClass("A", "X", 50),
        new MyClass("A", "Y", 20),
        new MyClass("B", "X", 30),
        new MyClass("B", "Y", 40)
    );

    // 按 field1 进行分组
    Map<String, List<MyClass>> groupedByField1 = myList.stream()
        .collect(Collectors.groupingBy(MyClass::getField1));
    System.out.println("按 field1 分组结果：" + groupedByField1);

    // 按 field1 和 field2 进行分组
    Map<String, Map<String, List<MyClass>>> groupedByField1AndField2 = myList.stream()
        .collect(Collectors.groupingBy(MyClass::getField1,
            Collectors.groupingBy(MyClass::getField2)));
    System.out.println("按 field1 和 field2 分组结果：" + groupedByField1AndField2);

    Map<String, List<MyClass>> groupedByField1AndField2_1 = myList.stream()
        .collect(Collectors.groupingBy(
            obj -> obj.getField1() + obj.getField2(),
            Collectors.toList()
        ));
String.valueOf(null);
    System.out.println("按 field1 和 field2 分组结果：" + groupedByField1AndField2_1);
    groupedByField1AndField2_1.forEach((x, y)->{
      y.forEach(System.out::print);
      System.out.println();
    });
  }
}


class MyClass {

  public String getField1() {
    return field1;
  }

  private String field1;

  public String getField2() {
    return field2;
  }

  private String field2;
  private int field3;

  public MyClass(String field1, String field2, int field3) {
    this.field1 = field1;
    this.field2 = field2;
    this.field3 = field3;
  }

  // getter 和 setter 方法
}