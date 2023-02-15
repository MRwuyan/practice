package example.offer;

import example.question.ListNode;

import java.util.HashSet;
import java.util.Set;

public class Day12 {
    /**
     * 输入：1->2->4, 1->3->4
     * 输出：1->1->2->3->4->4
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 输入两个链表，找出它们的第一个公共节点。
     *
     * @param headA
     * @param headB
     * @return
     */
    ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        ListNode temp = headA;
        while (temp != null) {
            if (!set.contains(temp)) {
                set.add(temp);
            }
            temp = temp.next;
        }
        temp = headB;
        while (temp!=null) {
            if (set.contains(temp)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }


    public static void main(String[] args) {
        Day12 day12 = new Day12();
        ListNode node = ListNode.getSortNode();
//        ListNode listNode = day12.mergeTwoLists(node, node);
    }
}
