package example.offer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

/**
 * 剑指 Offer 06. 从尾到头打印链表
 */
public class Solution {


    /**
     * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，
     * 每个节点除了有一个 next 指针指向下一个节点，
     * 还有一个 random 指针指向链表中的任意节点或者 null。
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {

        if (head == null) {
            return null;
        }
        HashMap<Node, Node> map = new HashMap<>();

        Node current = head;
        while (current != null) {
            int val = current.val;
            Node newNode = new Node(val);
            map.put(current, newNode);
            current = current.next;
        }
        current = head;
        while (current != null) {

            Node nextKey = current.next;
            Node randomKey = current.random;

            Node next = map.get(nextKey);
            Node random = map.get(randomKey);


            Node newNode = map.get(current);


            newNode.random = random;
            newNode.next = next;
            current = current.next;
        }
        return map.get(head);
    }
    HashMap<Node, Node> nodeNodeHashMap = new HashMap<>();
    public Node copyRandomList1(Node head) {

        if (head == null) {
            return null;
        }

        if (!nodeNodeHashMap.containsKey(head)) {
            Node newNode = new Node(head.val);
            nodeNodeHashMap.put(head, newNode);
            newNode.next = copyRandomList(head.next);
            newNode.random = copyRandomList(head.random);

        }
        return nodeNodeHashMap.get(head);
    }
    /**
     * 输入一个链表头部,从尾到头输出数组
     *
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        Deque<ListNode> listNode = new ArrayDeque<>();
        ListNode temp = head;
        while (temp != null) {
            listNode.push(temp);
            temp = temp.next;
        }
        int size = listNode.size();
        int[] ints = new int[size];
        for (int i = 0; i < size; i++) {
            ints[i] = listNode.pop().node;
        }
        return ints;
    }

    /**
     * 定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = prev;
            prev = head;
            head = temp;
        }
        return prev;
    }


    public static class ListNode {
        private int node;
        private ListNode next;

        public ListNode(int x) {
            this.node = x;
        }

        public int getNode() {
            return node;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public Solution.ListNode getNext() {
            return next;
        }

        public void setNext(Solution.ListNode next) {
            this.next = next;
        }
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
