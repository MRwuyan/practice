package example.offer;


public class Day10 {
    /**
     * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法
     * <p>
     * 如果条件成立:f[i]=f[i-1]+f[i-2]  (0<=x<=25)
     * 不成立:f[i]=f[i-2]
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        String s = String.valueOf(num);
        char[] chars = s.toCharArray();
        int dp[] = new int[chars.length];
        dp[0] = 1;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i - 1] == '0') {
                dp[i] = dp[i - 1];
                continue;
            }
            if (i == 1) {
                Integer x = Integer.valueOf(String.valueOf(chars[i - 1]) + String.valueOf(chars[1]));
                dp[i] = 2;
                if (x > 25) {
                    dp[i] = 1;
                }
                continue;
            }
            Integer x = Integer.valueOf(String.valueOf(chars[i - 1]) + String.valueOf(chars[i]));
            if (x > 25) {
                dp[i] = dp[i - 1];
                continue;
            }

            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[dp.length - 1];
    }

    /**
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        String pre = "";
        Integer r = 0;

        for (char c : chars) {
            String temp = String.valueOf(c);
            if (pre.contains(temp)) {
                pre = temp;
            } else {
                pre=pre+temp;
            }
            r = Math.max(pre.length(), r);
        }
        return r;
    }

    public static void main(String[] args) {
        Day10 day10 = new Day10();
        int i = day10.translateNum(506);
        int substring = day10.lengthOfLongestSubstring("abcabcbb");
        System.out.println(substring);
    }
}
