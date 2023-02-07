package example.offer;

import java.util.*;

public class Day5 {
    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        int right = numbers.length - 1;
        int left = 0;
        while (right > left) {
            int mid = (right - left) / 2 + left;
            if (numbers[mid] > numbers[right]) {
                left = mid + 1;
            } else if (numbers[mid] < numbers[right]) {
                right = mid;
            } else {
                int number = numbers[left];
                for (int i = left; i < right; i++) {
                    if (numbers[i] < number) {
                        number = numbers[i];
                    }
                }
                return number;
            }
        }
        return numbers[left];
    }

    /**
     * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        Map<Character, Boolean> map = new LinkedHashMap<>();
        for (char c : chars) {
            if (map.containsKey(c)) {
                map.put(c, false);
                continue;
            }
            map.put(c, true);
        }
        for (Character key : map.keySet()) {
            if (map.get(key)) {
                return key.charValue();
            }
        }
        return ' ';
    }
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int i = matrix.length-1;
        int j = 0;
        while (i >= 0 && j < matrix[0].length) {
            if (matrix[i][j] > target) {
                i--;
            } else if (matrix[i][j] < target) {
                j++;
            } else {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[] a = new int[]{3, 1, 1};
        int[][] b = new int[][]{{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        Day5 day5 = new Day5();
        boolean numberIn2DArray = day5.findNumberIn2DArray(b, 20);
        System.out.println(numberIn2DArray);
    }
}
