package com.roc.practice.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 变量引用
 */
public class VarDemo {
    public static void main(String[] args) {
        //匿名类引用外面的变量必须为final
        List<String> list = new ArrayList<>();
//        list = null;
        Consumer<Integer> a = i -> System.out.println(i + list.toString());
        a.accept(2);

    }
}
