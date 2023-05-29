package example.offer;

import sun.applet.Main;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * 包含min函数的栈
 */
public class MinStack {
    //推荐使用Deque，因为java的Stack是线程安全的，效率较低
    //ArrayDeque是线程不安全的，
    //Deque可以当普通队列、双端队列、堆栈

    Deque<Integer> dataQueue;
    Deque<Integer> minQueue;

    public MinStack() {
        //推荐使用Deque，因为java的Stack是线程安全的，效率较低
        //ArrayDeque是线程不安全的，
        //Deque可以当普通队列、双端队列、堆栈

        dataQueue = new ArrayDeque<>();
        minQueue = new ArrayDeque<>();
    }


    public void push(int x) {
        dataQueue.push(x);
        int min = x;
        if (!minQueue.isEmpty()) {
            min = minQueue.peek() > x ? x : minQueue.peek();
        }
        minQueue.push(min);
    }

    public void pop() {
        minQueue.pop();
        dataQueue.pop();
    }

    public int top() {
        return dataQueue.peek();
    }

    public int min() {
        return minQueue.peek();
    }

    public static void main(String[] args) {
        Deque<Integer> dataQueue = new ArrayDeque<>();
        dataQueue.push(11);
        System.out.println(dataQueue.peek());
        System.out.println(dataQueue.peek());
    }
}
