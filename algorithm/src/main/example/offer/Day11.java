package example.offer;

import example.question.ListNode;

public class Day11 {

    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        if (head.val == val) {
            return head.next;
        } else {
            head.next = deleteNode(head.next, val);
        }
        return head;
    }

    /**
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
     *
     * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
     *
     *
     * @param head
     * @param k
     * @return
     */
    /**
     * 给定一个链表: 1->2->3->4->5, 和 k = 2.
     *
     * 返回链表 4->5.
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode former = head, latter = head;
        for(int i = 0; i < k; i++)
            former = former.next;
        while(former != null) {
            former = former.next;
            latter = latter.next;
        }
        return latter;
    }
    public static void main(String[] args) {
        ListNode node = ListNode.getNode();
        Day11 day11 = new Day11();
        ListNode listNode = day11.deleteNode(node, 3);
        System.out.println(listNode);
    }
}
