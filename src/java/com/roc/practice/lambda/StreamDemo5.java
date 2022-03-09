package com.roc.practice.lambda;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * 并行流
 */
public class StreamDemo5 {
    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
//        new Random().ints().parallel().forEach(s -> StreamDemo5.add());
        //当有多个流声明时，取最后一次
//        long count = IntStream.range(1, 100).parallel().peek(s -> StreamDemo5.add()).sequential().peek(a -> StreamDemo5.add1()).limit(10).count();
        //并行流使用的线程ForkJoinPool.commonPool，（当前机器的cpu个数）
//        long count = IntStream.range(1, 100).parallel().peek(s -> StreamDemo5.add()).count();
//        java.util.concurrent.ForkJoinPool
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "20");
//        IntStream.range(1, 100).parallel().peek(a -> StreamDemo5.add()).count();
        ForkJoinPool pool = new ForkJoinPool(20);
        pool.submit(() -> IntStream.range(1, 100).parallel().peek(a -> StreamDemo5.add()).count());
        pool.shutdown();
        Thread.sleep(10000L);
    }

    public static int add() {
        i++;
        System.err.println(Thread.currentThread().getName() + "-->" + i);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int add1() {
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
