package com.roc.practice.lambda;

import java.util.Random;

public class StreamDemo5 {
    private static int i=0;
    public static void main(String[] args) {
        new Random().ints().parallel().forEach(s-> StreamDemo5.add());
    }
    public static int add(){
        i++;
        System.out.println(i);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }
}
