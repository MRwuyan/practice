package com.Interview;

public class StringDemo {
    public static void main(String[] args) {
        testRound();
    }

    private static void testString() {
        //下面的语句会创建几个对象
        String a = "hello";
        String b = new String("hello");
        System.out.println(a == b);
        String b1 = new String("hello1");
        String a1 = "hello1";
        System.out.println(a1 == b1);


        String s = "12";
        String s1 = "34";
        String s2 = s + s1;
        String s3 = "1234";
        String s4 = "12" + "34";
        String s5 = s + "34";
        String s6 = "12" + new String("34");
        System.out.println("----------------------");
// 第一题
        System.out.println(s2 == s3); // false
// 第二题
        System.out.println(s2 == s4); // false
// 第三题
        System.out.println(s3 == s4); // true
// 第四题
        System.out.println(s3 == s5); // false
// 第五题
        System.out.println(s2 == s5); // false
// 第六题
        System.out.println(s3 == s6); // false
        //https://blog.csdn.net/qq_32625839/article/details/82634076
    }

    private static void testDouble(){
        int a = 4;
        int b = 3;
        double c = a / b;
        System.out.println(c);
    }
    private static void testRound(){
        long round = Math.round(-20.7);
        long round1 = Math.round(17.46);
        System.out.println(round);
        System.out.println(round1);
    }
}
