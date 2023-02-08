package example.question;


public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode getNode() {
        ListNode treeNode3 = new ListNode(3);
        ListNode treeNode9 = new ListNode(9);

        ListNode treeNode20 = new ListNode(20);
        ListNode treeNode15 = new ListNode(15);
        ListNode treeNode7 = new ListNode(7);

        treeNode3.next = treeNode9;
        treeNode9.next = treeNode20;
        treeNode20.next =treeNode15 ;
        treeNode15.next = treeNode7;
        return treeNode3;
    }
}
