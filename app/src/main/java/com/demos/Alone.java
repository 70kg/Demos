package com.demos;

import java.util.Map;

public class Alone {

    public static void main(String[] args) {
        String s = "Mr John Smith 13";
        char[] chars = s.toCharArray();
//        replaceBlank(chars, 13);
        int f = 10 << 1;
        System.out.println(f + " ");


        for (Map.Entry entry : System.getenv().entrySet()) {
            System.out.println(entry.getKey() + "--" + entry.getValue());
        }
    }


    public static class TreeNode {
        public int val;
        public TreeNode left, right;

        public TreeNode(int val) {
            this.val = val;
            this.left = this.right = null;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    //等价二叉树
    public boolean isIdentical(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.val != b.val) return false;
        if (isIdentical(a.left, b.left) && isIdentical(a.right, b.right)) {
            return true;
        }
        return false;
    }

    //两两交换链表中的节点
    public ListNode swapPairs(ListNode head) {
        ListNode p = head, q = head.next, pre = null;
        head = null;
        while (p != null && q != null) {
            p.next = q.next;
            q.next = p;
            if (pre != null) {
                pre.next = q;
            }
            pre = p;
            if (head == null) {
                head = q;
            }
            p = p.next;
            if (p != null) {
                q = p.next;
            }
        }
        if (head == null) head = p;
        return head;
    }

    //翻转二叉树
    public TreeNode invertBinaryTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode tem = root.left;
        root.left = invertBinaryTree(root.right);
        root.right = invertBinaryTree(tem);
        return root;
    }

    //"Mr John Smith 13  替换空格
    public static int replaceBlank(char[] string, int length) {
        for (int i = 0; i < length; i++) {
            if (string[i] == ' ') {
                for (int j = length + 2; j > i + 2; j--) {
                    string[j] = string[j - 2];
                }
                string[i] = '%';
                string[i + 1] = '2';
                string[i + 2] = '0';
                length += 2;
            }

        }
        return length;
    }

}
