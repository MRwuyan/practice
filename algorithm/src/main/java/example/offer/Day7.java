package example.offer;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.nio.channels.NonWritableChannelException;

public class Day7 {
    /**
     * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
     * <p>
     * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
     *
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (B == null || A == null) {
            return false;
        }
        return help(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    private boolean help(TreeNode a, TreeNode b) {
        if (b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }

        if (a.val != b.val) {
            return false;
        }
        return help(a.left, b.left) && help(a.right, b.right);
    }

    /**
     * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
     *
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode treeNode = new TreeNode(root.val);
        helpMirrorTree(root, treeNode);
        return treeNode;
    }

    private void helpMirrorTree(TreeNode oldNode, TreeNode newNode) {

        if (oldNode == null) {
            return;
        }
        if (oldNode.left != null) {
            newNode.right = new TreeNode(oldNode.left.val);
        }
        if (oldNode.right != null) {
            newNode.left = new TreeNode(oldNode.right.val);
        }
        helpMirrorTree(oldNode.right, newNode.left);
        helpMirrorTree(oldNode.left, newNode.right);
    }

    /**
     * 对称的二叉树
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {

        if (root == null) {
            return true;
        }
        TreeNode treeNode = root;
        return helpIsSymmetric(root, treeNode);

    }

    private boolean helpIsSymmetric(TreeNode oldNode, TreeNode newNode) {
        if (oldNode == null && newNode == null) {
            return true;
        }
        if (oldNode == null || newNode == null) {
            return false;
        }
        if (oldNode.val != newNode.val) {
            return false;
        }
        return helpIsSymmetric(oldNode.left, newNode.right) && helpIsSymmetric(oldNode.right, newNode.left);
    }

    public static void main(String[] args) {
        Day7 day7 = new Day7();
        TreeNode node = TreeNode.getNode();
        TreeNode treeNode = day7.mirrorTree(node);
        System.out.println(treeNode);
    }


}
