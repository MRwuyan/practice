package practice.threadLocal;

import java.util.concurrent.TimeUnit;

public class InterruptDemo {
    public static void main(String[] args) {

        interrupted();

    }

    private static void interrupted() {
        System.out.println("interrupted->" + Thread.interrupted());
        System.out.println("interrupted->" + Thread.interrupted());
        Thread.currentThread().interrupt();
        System.out.println("interrupted->" + Thread.interrupted());
        System.out.println("interrupted->" + Thread.interrupted());
    }

    private static void m1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("i====" + i);
            }
            System.out.println("t1.interrupt调用后:" + Thread.currentThread().isInterrupted());

        }, "t1");
        t1.start();
        t1.interrupt();
        System.out.println("t1.interrupt调用后:" + Thread.currentThread().isInterrupted());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("t1.interrupt调用后:" + Thread.currentThread().isInterrupted());
    }

    private static void interrupt() {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("-----isInterrupted() = true，程序结束。");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("------hello Interrupt");
            }
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }
}
