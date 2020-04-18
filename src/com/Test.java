package com;

import com.sun.xml.internal.ws.util.StringUtils;
import sun.rmi.runtime.Log;

public class Test {
    public static void main(String[] args) {
        Integer i = 129;
        Integer j = 129;
        System.out.println(i == j);
        System.out.println(i.compareTo(j));
        System.out.println(i.equals(j));
        System.out.println("...................................");
//        String str1 = "meng";
//        String str2 = "meng";
//        String str3 = new String("meng");
//        System.out.println(str1 == str2);
//        System.out.println(str1 == str3);
        System.out.println(Integer.MAX_VALUE );
        System.out.println(Integer.MAX_VALUE + 1);
        System.out.println(Integer.MIN_VALUE);
        String str1= "abc";
        String str2= new String("abc");
        String str3= str2.intern();
        System.out.println(str1==str2);
        System.out.println(str2==str3);
        System.out.println(str1==str3);
        System.out.println(Integer.toBinaryString(-8));
        System.out.println(Integer.toBinaryString(-7));
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Long.toString(Long.MAX_VALUE,36));
        String seqStr = Long.toString(Long.MAX_VALUE,36);
        String numSeqStr = Long.toString(Long.MAX_VALUE);


    }
}
