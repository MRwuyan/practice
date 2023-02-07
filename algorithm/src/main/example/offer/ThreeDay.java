package example.offer;

public class ThreeDay {
    /**
     * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
     * 输入：s = "We are happy."
     * 输出："We%20are%20happy."
     * @param s
     * @return
     */
    public static String replaceSpace(String s) {
        return s.replace(" ", "%20");
    }

    /**
     * 剑指 Offer 58 - II. 左旋转字符串
     * 输入: s = "abcdefg", k = 2
     * 输出: "cdefgab"
     * @param s
     * @param n
     * @return
     */
    public static String reverseLeftWords(String s, int n) {
        String substring = s.substring(2);
        String s1 = s.substring(0,2);
        System.out.println(substring);
        System.out.println(s1);
        return s;
    }
    public static void main(String[] args) {
        String s = reverseLeftWords("abcdefg",2);
        System.out.println(s);
    }



}
