package com.roc.practice.lambda;

interface IMath {
    Integer add(int x, int y);
}
interface IMath2 {
    Integer add(int x, int y);
}

/**
 * 类型推断
 */
public class TypeDemo {
    public static void main(String[] args) {
        //变量类型定义
        IMath iMath = (x, y) -> x + y;
        iMath.add(1, 1);
        IMath[] iMaths = {(x, y) -> x + y};
        for (IMath math : iMaths) {
            math.add(1, 2);
        }
        //强转
        Object oj = (IMath) (x, y) -> x + y;
        //通过返回类型
        IMath createAdd = createAdd();
        createAdd.add(1, 1);
        TypeDemo typeDemo = new TypeDemo();
        //当有二义性的时候，使用强转对应的接口解决
        typeDemo.test((IMath) (x, y) -> x + y);
    }

    public static IMath createAdd() {
        return (x, y) -> x + y;
    }

    public void test(IMath iMath) {
    }
    public void test(IMath2 iMath) {
    }
}
