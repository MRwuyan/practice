package practice.threadLocal;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportDemo {
    static Object object = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        m3();

    }

    private static void m3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            LockSupport.park();
            System.out.println("1");
        }, "t1");
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println("2");
        }, "t2").start();
    }

    private static void m2() throws InterruptedException {
        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
                System.out.println("1");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        }, "t1").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println("2");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void m1() throws InterruptedException {

        new Thread(() -> {
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("1");
            }
        }, "t1").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            synchronized (object) {
                object.notify();
                System.out.println("2");
            }
        }, "t2").start();
    }
}
