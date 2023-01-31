package example.offer;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 09. 用两个栈实现队列
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 */
public class CQueue {
    //推荐使用Deque，因为java的Stack是线程安全的，效率较低
    //ArrayDeque是线程不安全的，
    //Deque可以当普通队列、双端队列、堆栈

    Deque<Integer> inputDeque;
    Deque<Integer> outputDeque;
    public CQueue() {
        this.inputDeque = new ArrayDeque<>();
        this.outputDeque = new ArrayDeque<>();
    }

    public void appendTail(int value) {
        //在添加元素之前先判断 栈2 是否为空
        //如果栈2 不为空，则说明在 栈2 进行过元素的删除
        //因为删除元素之前需要把 栈1的元素添加至栈2

        //为了保证元素顺序地一致性
        //把栈2 的元素依次弹出，添加至 栈1
        while(!outputDeque.isEmpty()){
            inputDeque.push(outputDeque.pop());
        }
        inputDeque.push(value);
    }

    public int deleteHead() {
        //因为栈的特性是 先进后出
        //如果要删除头部的元素，则需要将栈倒置
        //如果栈1 不为空，就将栈1元素依次弹出至 栈2
        //然后删除栈2 的栈顶元素
        //如果栈2为空，则无法删除，返回-1
        while(!inputDeque.isEmpty()){
            outputDeque.push(inputDeque.pop());
        }
        if(outputDeque.isEmpty()){
            return -1;
        }
        return outputDeque.pop();
    }

}
