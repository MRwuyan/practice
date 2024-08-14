package com.Interview;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {

    public static final int SIZE_THREAD = 50;
    public static final int _1w = 10000;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long start;
        long end;
        start = System.currentTimeMillis();
        CountDownLatch countDownLatch1 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch2 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch3 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch4 = new CountDownLatch(SIZE_THREAD);
        CountDownLatch countDownLatch5 = new CountDownLatch(SIZE_THREAD);
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < _1w * 100; j++) {
                        clickNumber.add_sync();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }).start();
        }
        countDownLatch1.await();
        end = System.currentTimeMillis();
        System.out.println("sync:" + (end - start) + "\t" + clickNumber.number);

        start = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < _1w * 100; j++) {
                        clickNumber.add_atomic();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            }).start();
        }
        countDownLatch2.await();
        end = System.currentTimeMillis();
        System.out.println("atomic:" + (end - start) + "\t" + clickNumber.atomicInteger.get());

        start = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < _1w * 100; j++) {
                        clickNumber.add_atomicLong();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            }).start();
        }
        countDownLatch3.await();
        end = System.currentTimeMillis();
        System.out.println("atomicLong:" + (end - start) + "\t" + clickNumber.atomicLong.get());

        start = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < _1w * 100; j++) {
                        clickNumber.add_longAdder();
                    }
                } finally {
                    countDownLatch4.countDown();
                }
            }).start();
        }
        countDownLatch4.await();
        end = System.currentTimeMillis();
        System.out.println("longAdder:" + (end - start) + "\t" + clickNumber.longAdder.sum());

        start = System.currentTimeMillis();
        for (int i = 0; i < SIZE_THREAD; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < _1w * 100; j++) {
                        clickNumber.longAccumulator.accumulate(1);
                    }
                } finally {
                    countDownLatch5.countDown();
                }
            }).start();
        }
        countDownLatch5.await();
        end = System.currentTimeMillis();
        System.out.println("longAccumulator:" + (end - start) + "\t" + clickNumber.longAccumulator.get());
    }
}

class ClickNumber {
    int number = 0;

    public synchronized void add_sync() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();

    public void add_atomic() {
        atomicInteger.incrementAndGet();
    }

    AtomicLong atomicLong = new AtomicLong();

    public void add_atomicLong() {
        atomicLong.incrementAndGet();
    }

    LongAdder longAdder = new LongAdder();

    public void add_longAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);


}