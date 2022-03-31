package practice.lambda;

import java.util.function.Function;

/**
 * 实现级联表达式和柯里表达式
 */
public class CurryDemo {
    public static void main(String[] args) {
        //实现级联表达式
        Function<Integer, Function<Integer, Integer>> a = x -> y -> x + y;
        Integer apply = a.apply(1).apply(1);
        System.out.println(apply);
    }
}
