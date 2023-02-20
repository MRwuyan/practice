package example.offer;

public class Day14 {
    /**
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * <p>
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母不允许被重复使用。
     * <p>
     * <p>
     * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * 输出：true
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (check(board, words, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param board
     * @param words
     * @param i
     * @param j
     * @param k
     * @return
     */
    private boolean check(char[][] board, char[] words, int i, int j, int k) {
        if (i < 0 || j < 0 || j >= board[0].length || i >= board.length || words[k] != board[i][j]) {
            return false;
        }
        if (k == words.length - 1) {
            return true;
        }
        board[i][j] = '#';

        boolean res = check(board, words, i, j - 1, k + 1)
                || check(board, words, i - 1, j, k + 1)
                || check(board, words, i, j + 1, k + 1)
                || check(board, words, i + 1, j, k + 1);
        board[i][j] = words[k];
        return res;
    }

    /**
     * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，
     * 它每次可以向左、右、上、下移动一格（不能移动到方格外），
     * 也不能进入行坐标和列坐标的数位之和大于k的格子。
     * 例如，当k为18时，机器人能够进入方格 [35, 37] ，
     * 因为3+5+3+7=18。但它不能进入方格 [35, 38]，
     * 因为3+5+3+8=19。请问该机器人能够到达多少个格子？
     * 输入：m = 2, n = 3, k = 1
     * 输出：3
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    int m, n, k;
    boolean[][] visited;

    public int movingCount(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.visited = new boolean[m][n];
        return dfs(0, 0, 0, 0);
    }

    public int dfs(int i, int j, int si, int sj) {
        if (i >= m
                || j >= n
                || k < si + sj
                || visited[i][j])
            return 0;

        visited[i][j] = true;
        return 1
                +dfs(i + 1, j, (i + 1) % 10 != 0 ? si + 1 : si - 8, sj)
                + dfs(i, j + 1, si, (j + 1) % 10 != 0 ? sj + 1 : sj - 8);
    }

    /**
     * 求和
     *
     * @param x
     * @return
     */
    int sums(int x) {
        int s = 0;
        while (x != 0) {
            s += x % 10;
            x = x / 10;
        }
        return s;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        Day14 day14 = new Day14();
        boolean see = day14.exist(board, "SEE");
        int i = day14.movingCount(2, 3, 1);
        System.out.println(i);
        System.out.println(see);
    }
}
