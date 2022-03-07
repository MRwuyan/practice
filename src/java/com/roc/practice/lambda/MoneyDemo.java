package com.roc.practice.lambda;

import java.text.DecimalFormat;
import java.util.function.Function;


class MyMoney {
    private int money;

    public MyMoney(int money) {
        this.money = money;
    }

    public void format(Function<Integer, String> format) {
        System.out.println("我的money:" + format.apply(money));
    }
}

public class MoneyDemo {
    public static void main(String[] args) {
        MyMoney money = new MyMoney(10000000);
        money.format((i) -> new DecimalFormat("#,###").format(i));
    }
}
