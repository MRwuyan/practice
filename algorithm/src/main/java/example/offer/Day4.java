package example.offer;

import java.util.HashSet;
import java.util.logging.Level;

public class Day4 {
    public static int findRepeatNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return nums[i];
                }
            }
        }
        return -1;
    }

    public static int findRepeatNumber1(int[] nums) {
        HashSet<Integer> integers = new HashSet<>();
        int re = -1;
        for (int num : nums) {
            if (!integers.add(num)) {
                re = num;
                break;
            }
        }
        return re;
    }

    public int search(int[] nums, int target) {
        int count = 0;
        for (int num : nums) {
            if (num == target) {
                count++;
            }
        }
        return count;
    }

    static int cnt = 0;

    public static int search1(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        helper(nums, target, low, high);
        return cnt;
    }

    public static void helper(int[] nums, int target, int low, int high) {
        if (low <= high) {
            int mid = ((high - low) / 2) + low;
            if (nums[mid] == target) {
                cnt++;
                helper(nums, target, low, mid - 1);
                helper(nums, target, mid + 1, high);
            } else if (nums[mid] > target) {
                helper(nums, target, low, mid - 1);
            } else {
                helper(nums, target, mid + 1, high);
            }
        }
    }

    /**
     * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
     * 输入: [0,1,3]
     * 输出: 2
     *
     * @param nums
     * @return
     */
    public static int missingNumber(int[] nums) {
        int low = 0, high = nums.length - 1;

        return helper(nums, low, high);
    }

    private static int helper(int[] nums, int low, int high) {
        int mid = (high - low) / 2 + low;
        if (low>high) {
            return low;
        }
        if (nums[mid] == mid) {
            low = mid + 1;
            return helper(nums, low, high);
        } else {
            high = mid - 1;
            return helper(nums, low, high);
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        int repeatNumber = missingNumber(a);
        System.out.println(repeatNumber);
    }
}
