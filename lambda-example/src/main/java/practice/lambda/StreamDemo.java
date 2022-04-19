package practice.lambda;


import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
//使用stream的内部迭代
//map，flatMap：中间操作（返回stream是中间操作）
//sum 终止操作
public class StreamDemo {

    public static void main(String[] args) {
        int[] num = {1, 2, 3};
        double[] doubles = {1D, 2d, 3d};
        int sum = IntStream.of(num).flatMap(i-> IntStream.of(i+1)).map(i->i+1).sum();
        double sum1= DoubleStream.of(doubles).flatMap(i-> DoubleStream.of(i+1d)).map(i->i+1).sum();
        System.out.println(sum);
    }
}
