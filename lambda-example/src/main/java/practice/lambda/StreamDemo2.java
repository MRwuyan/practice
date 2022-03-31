package practice.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流的创建
 */
public class StreamDemo2 {
    public static void main(String[] args) {
        //集合 collection.stream/parallelStream
        List<String> list = new ArrayList<>();
        list.stream();
        list.parallelStream();
        //数组 Arrays.stream
        Arrays.stream(new int[]{2, 3, 4});
        //数字stream intStream/longStream.range/rangeClosed/Random.ints/doubles
        IntStream.of(3, 4, 5);
        IntStream limit = new Random().ints().limit(10);
        //自己创建 Stream.generate/iterate
        Random random = new Random();
        Stream<Integer> limit1 = Stream.generate(() -> random.nextInt()).limit(20);
        long count = limit.count();
        System.out.println(count);
    }
}
