package com.roc.practice.lambda;

/**
 * 1.函数式接口：FunctionalInterface（编译期间的校验）
 * 接口里面*要实现的*只能有一个方法才能被lambda使用
 */
@FunctionalInterface
interface Interface1 {
    int doub(int i);

    default int add(int a, int b) {
        return a + b;
    }

}

@FunctionalInterface
interface Interface2 {
    int doub(int i);

    default int add(int a, int b) {
        return a + b;
    }

}

@FunctionalInterface
interface Interface3 extends Interface1, Interface2 {


    @Override
    default int add(int a, int b) {
        return Interface1.super.add(a, b);
    }
}

public class LambdaDemo1 {
    /**
     * 4种调用方式
     *
     * @param args
     */
    public static void main(String[] args) {
        Interface1 a = (i) -> i * 1;
        Interface1 b = i -> i * 2;
        Interface1 c = (int i) -> i * 3;
        Interface1 d = (int i) -> {
            return i * 4;
        };
        System.out.println(a.doub(1));
        System.out.println(b.doub(1));
        System.out.println(c.doub(1));
        //
        System.out.println(d.doub(1));
        System.out.println(a.add(1, 2));

        Interface3 i3 = ((i) -> i * 2);
    }
}
