package example.offer;

public class Day9 {
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    /**
     * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        //dp[i][j]表示从grid[0][0]到grid[i - 1][j - 1]时的最大价值
        int[][] dp = new int[row + 1][column + 1];
        for (int i = 1; i <= grid.length; i++) {
            for (int j = 1; j <= column; j++) {
                int a = dp[i - 1][j];
                int b = dp[i][j - 1];
                int c = grid[i - 1][j - 1];
                dp[i][j] = Math.max(a, b) + c;
            }
        }
        return dp[row][column];
    }

    public static void main(String[] args) {
        int[] ints = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        Day9 day9 = new Day9();
        int i = day9.maxSubArray(ints);
        int[][] maxVals = new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        int i1 = day9.maxValue(maxVals);
        System.out.println(i1);
    }
}
