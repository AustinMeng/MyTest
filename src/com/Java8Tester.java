package com;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class Java8Tester {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        long count = strings.stream().filter(string->!string.isEmpty()).count();
        System.out.println(count);

        List<String> filtered = strings.stream().filter(string->!string.isEmpty()).collect(Collectors.toList());
        System.out.println(filtered);

        String mergedString = strings.stream().filter(string->!string.isEmpty()).collect(Collectors.joining(","));
        System.out.println(mergedString);

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squares = numbers.stream().map(i->i*i).sorted().collect(Collectors.toList());
        System.out.println(squares);

        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);
        IntSummaryStatistics intSummaryStatistics = integers.stream().mapToInt(x->x).summaryStatistics();
        System.out.println("The max:"+intSummaryStatistics.getMax());

        //Javascript
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashon = scriptEngineManager.getEngineByName("nashorn");
        Integer result = null;
        try {
            nashon.eval("print('"+"Austin"+"')");
            result = (Integer) nashon.eval("10+2");

        } catch (ScriptException e) {
            e.printStackTrace();
        }
        System.out.println(result.toString());
    }
}
