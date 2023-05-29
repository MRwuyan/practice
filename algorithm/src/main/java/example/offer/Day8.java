package example.offer;

public class Day8 {
    /**
     * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        final int MOD = 1000000007;
        if (n < 2) {
            return n;
        }
        int a = 0, b = 0, m = 1;
        for (int i = 2; i <= n; ++i) {
            a = b;
            b = m;
            m = (a + b) % MOD;
        }
        return m;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
     * <p>
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1
     *
     * @param n
     * @return
     */
    public int numWays(int n) {
        if (n == 0) {
            return 1;
        }

        int i = helpNumWays(n);
        return i;
    }


    private int helpNumWays(int n) {
        final int MOD = 1000000007;
        if (n < 3) {
            return n % MOD;
        }
        int a = 0, b = 1, r = 2;
        for (int i = 3; i <= n; i++) {
            a = b;
            b = r;
            r = (a + b) % MOD;
        }
        return r;
    }

    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int min = prices[0], r = 0;
        for (int i = 1; i < prices.length; i++) {
            if (min > prices[i]) {
                min = prices[i];
            } else {
                if ((prices[i] - min) > r) {
                    r = prices[i] - min;
                }
            }
        }
        return r;
    }

    public static void main(String[] args) {
        Day8 day8 = new Day8();
        int fib = day8.fib(3);
        int numWays = day8.numWays(44);
        int[] prices = new int[]{};
        int i = day8.maxProfit(prices);
        System.out.println(i);
    }

}

