package practice.threadLocal;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级demo
 */
public class LockDownGradingDemo {

    public static void main(String[] args) {
        // ReentrantReadWriteLock 降级demo
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        reentrantReadWriteLock.writeLock().lock();
        System.out.println("write lock");

        reentrantReadWriteLock.readLock().lock();
        System.out.println("read lock");


        reentrantReadWriteLock.writeLock().unlock();
        System.out.println("write unlock");
    }
}
