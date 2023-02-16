package example.offer;

public class Day13 {
    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数在数组的前半部分，所有偶数在数组的后半部分。
     * 输入：nums = [1,2,3,4]
     * 输出：[1,3,2,4]
     * 注：[3,1,2,4] 也是正确的答案之一。
     *
     * @param nums
     * @return
     */

    public int[] exchange(int[] nums) {
        int length = nums.length;
        int[] newInt = new int[length];

        int left = 0, right = length - 1;
        for (int num : nums) {
            if (num % 2 == 0) {
                newInt[right--] = num;
            } else {
                newInt[left++] = num;
            }
        }
        return newInt;
    }

    /**
     * 输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[2,7] 或者 [7,2]
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length; j++) {
                int r = nums[i] + nums[j];
                if (r < target) {
                    continue;
                } else if (r == target) {
                    return new int[]{nums[i], nums[j]};
                } else {
                    break;
                }
            }
        }
        return null;
    }

    /**
     * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。
     * 例如输入字符串"I am a student. "，则输出"student. a am I"。
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        String[] strings = s.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = strings.length-1;i>=0;i--){
            String sss = strings[i];
            if(strings[i] == "") continue;
            sb.append(strings[i].trim());
            if(i !=0){
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Day13 day13 = new Day13();
        String the_sky_is_blue = day13.reverseWords("the sky is blue");
        System.out.println(the_sky_is_blue);

    }
}
