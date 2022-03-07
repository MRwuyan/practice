package com.roc.practice.lambda;


import java.util.function.*;

class Dog {
    public Dog(Integer num) {
        this.num = num;
    }

    public Dog() {
    }

    private Integer num = 10;

    public static String name(String s) {
        System.out.println(s);
        return s;
    }

    public Integer eat(int a) {
        num -= a;
        System.out.println("剩下：" + num);
        return num;
    }
}

public class MethodRefDemo {

    public static void main(String[] args) {
        System.out.println("");
        //静态
        Function<String, String> name = Dog::name;
        String ss = name.apply("ss");
        //非静态调用，实例调用
        Dog dog = new Dog();
        Function<Integer,Integer> eat = dog::eat;
        eat.apply(1);
//        IntUnaryOperator eat = dog::eat;
//        eat.applyAsInt(1);
        //方法引用，类名调用
        BiFunction<Dog, Integer, Integer> eat1 = Dog::eat;
        eat1.apply(dog, 1);
        //构造函数的方法应用
        Supplier<Dog> aNew = Dog::new;
        Dog dog1 = aNew.get();
        Function<Integer,Dog> dog2 = Dog::new;
        dog2.apply(1).eat(1);
    }
}
