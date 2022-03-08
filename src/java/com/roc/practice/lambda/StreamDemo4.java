package com.roc.practice.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 终止操作
 */
public class StreamDemo4 {
    public static void main(String[] args) {
        //非短路操作：forEach forEachOrderEd
        //collect、 toArray
        //reduce
        //min/max/count
        String a = "my name is ddd";
        a.chars().parallel().forEach(i -> System.out.print((char) i));
        System.out.println();
        a.chars().parallel().forEachOrdered(i -> System.out.print((char) i));
        System.out.println();
        List<String> list = Stream.of(a.split(" ")).collect(Collectors.toList());
        Optional<String> reduce = Stream.of(a.split(" ")).reduce((s1, s2) -> s1 + "-" + s2);
        reduce.orElse("ddd");
        if (reduce.isPresent()) {
            System.out.println(reduce.get());
        }
        String reduce1 = Stream.of("").reduce("dd", (s1, s2) -> s1 + "-" + s2);
        System.out.println("reduce1====" + reduce1);
//        Optional<String> max = Stream.of(a.split(" ")).max((s1, s2) -> s1.length() - s2.length());
        Optional<String> max = Stream.of(a.split(" ")).max(Comparator.comparingInt(String::length));
        String s = max.get();
        System.out.println(s);
        long count = Stream.of(a.split(" ")).count();
        System.out.println(count);
        /*短路操作*/
        //findFirst/findAny
        //allMatch/anyMatch/noneMatch
        String s1 = Stream.of(a.split(" ")).findFirst().get();
        System.out.println(s1);
        System.out.println("===========findAny================================");
        String s2 = Stream.of(a.split(" ")).parallel().findAny().get();
        System.out.println(s2);
        List<String> lst1 = Arrays.asList("Jhonny", "David", "Jack", "Duke", "Jill", "Dany", "Julia", "Jenish", "Divya");
        List<String> lst2 = Arrays.asList("Jhonny", "David", "Jack", "Duke", "Jill", "Dany", "Julia", "Jenish", "Divya");

        Optional<String> findFirst = lst1.parallelStream().filter(b -> b.startsWith("D")).findFirst();
        Optional<String> fidnAny = lst2.parallelStream().filter(b -> b.startsWith("J")).findAny();
        System.out.println("***************************");
        System.out.println(findFirst.get()); //总是打印出David
        System.out.println(fidnAny.get()); //会随机地打印出Jack/Jill/Julia

    }
}
