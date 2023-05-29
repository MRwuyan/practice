package example.oneweek;

public class Day1 {
    public static void main(String[] args) {
        int a = 1;
        int b=2;
        a=a^b;
        b=a^b;
        a=a^b;
        System.out.println("a="+a+",b="+b);
        int eor = 10;
        int rightOne = eor & (~eor + 1);
        System.out.println(rightOne);
    }
    //解法：
//    T [n] = aT[n/b] + f (n)（直接记为T [n] = aT[n/b] + O (N^d)）
    //①当d<logb a时，时间复杂度为O(n^(logb a))
    //②当d=logb a时，时间复杂度为O((n^d)*logn)
    //③当d>logb a时，时间复杂度为O(n^d)
}
