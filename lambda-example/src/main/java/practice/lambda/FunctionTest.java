package practice.lambda;


import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionTest {
    public static void main(String[] args) {
        //断言
        Predicate<Integer> predicate = (i)-> i>0;
        System.out.println(predicate.test(1));
        //消费者
        Consumer<Integer> consumer = (i) -> System.out.println(i);
        consumer.accept(2);
        //提供一个数据
        Supplier<Integer> supplier = ()-> 1;
    }


}
