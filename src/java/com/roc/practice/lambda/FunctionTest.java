package com.roc.practice.lambda;


import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionTest {
    public static void main(String[] args) {
        Predicate<Integer> predicate = (i)-> i>0;
        System.out.println(predicate.test(1));
        Consumer<Integer> consumer = (i) -> System.out.println(i);
        consumer.accept(2);
        Supplier<Integer> supplier = ()-> 1;
    }


}
