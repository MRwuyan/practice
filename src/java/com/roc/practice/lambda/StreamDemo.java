package com.roc.practice.lambda;


import java.util.stream.IntStream;
//使用stream的内部迭代
//map，flatMap：中间操作（返回stream是中间操作）
//sum 终止操作
public class StreamDemo {

    public static void main(String[] args) {
        int[] num = {1, 2, 3};
        int sum = IntStream.of(num).flatMap(i-> IntStream.of(i+1)).map(i->i+1).sum();
        System.out.println(sum);
    }
}
