package com.roc.practice.lambda;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream的运行机制、
 * 1.所有操作是链式调用，一个元素只迭代一次
 * 2.每个中间操作返回一个新的流，流里面有一个属性sourceStage，指向同一个地方，就是head
 * 3。Head-》nextStage->...->null
 * 4.有状态操作会把无状态的中间操作统一单独处理
 * 5.
 * 6.parallel/sequetial 这2个操作也是中间操作（也是返回Stream）但是他们不创建流，只是修改了head的并行标志
 */
public class StreamDemo7 {
    public static void main(String[] args) {
        Stream.generate(()->new Random().nextInt())
                //无状态
                .filter(i ->{
                    System.out.println("filter:");
                    return i < 10000 && i>0;
                })
                //无状态
                .limit(10)
                //无状态
                .peek(p1-> System.out.println("peek2==="+p1))
                //有状态
                .sorted((s1, s2) ->{
                    System.out.println("sort===:"+s1+","+s2);
                    return s1.compareTo(s2);
                })
                //无状态
                .peek(p2-> System.out.println("peek2==="+p2))
                .count();
    }
}
