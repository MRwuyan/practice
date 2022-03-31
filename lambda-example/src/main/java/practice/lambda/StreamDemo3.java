package practice.lambda;


import java.util.Random;
import java.util.stream.Stream;

/**
 * 中间操作
 */
public class StreamDemo3 {
    public static void main(String[] args) {
        //有状态 map，mapToxxx，flatMap。。
        String a = "my name is ddd";
        Stream.of(a.split(" ")).map(b->b.length()).forEach(System.out::println);
        System.out.println("-------------------------");
        // longStream /intStream 并不是Stream的子类，所以要进行装箱 boxed
        Stream.of(a.split(" ")).flatMap(b->b.chars().boxed()).forEach(i->{
            System.out.print((char)i.intValue());
        });
        System.out.println("=====================================");
        Stream.of(a.split(" ")).peek(System.out::println).peek(System.out::println).forEach(i->{
            System.out.println(i+"//////");
        });
        //无状态 distinct sorted limit
        System.out.println("=========================limit=================================");
        Stream.of(new Random().nextInt()).limit(10).forEach(System.out::println);
        System.out.println("=========================filter=================================");
        new Random().ints().filter(i->i>1&&i<10).limit(10).forEach(System.out::println);
    }
}
