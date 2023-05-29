package example.question;

import java.util.HashMap;
import java.util.LinkedList;

public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return helpTwoNumbers(l1, l2, 0);
    }

    private ListNode helpTwoNumbers(ListNode l1, ListNode l2, int bit) {
        if (l1 == null && l2 == null && bit == 0) {
            return null;
        }
        int val = bit;
        if (l1 != null) {
            val += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            val += l2.val;
            l2 = l2.next;
        }
        ListNode node = new ListNode(val % 10);
        node.next = helpTwoNumbers(l1, l2, val / 10);
        return node;
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int max = 0, start = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (map.containsKey(ch)){
                start = Math.max(map.get(ch)+1,start);
            }
            max = Math.max(max,i - start + 1);
            map.put(ch,i);
        }
        return max;
    }
    public static void main(String[] args) {
        ListNode node = ListNode.getNode();
        Solution solution = new Solution();
        solution.addTwoNumbers(node, node);
        solution.lengthOfLongestSubstring("abcabcbb");
    }
}
