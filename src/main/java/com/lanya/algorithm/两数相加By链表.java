package com.lanya.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 两数相加
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/8 3:59 下午
 */
public class 两数相加By链表 {

    public static void main(String[] args) {
        //ListNode l16 = new ListNode(6);
        //ListNode l15 = new ListNode(5, l16);
        //ListNode l14 = new ListNode(5, l15);
        ListNode l13 = new ListNode(3);
        ListNode l12 = new ListNode(4, l13);
        ListNode l11 = new ListNode(2, l12);
        // 555556
        // 4447
        //ListNode l24 = new ListNode(7);
        ListNode l23 = new ListNode(4);
        ListNode l22 = new ListNode(6, l23);
        ListNode l21 = new ListNode(5, l22);

        两数相加By链表 obj = new 两数相加By链表();
        ListNode listNode = obj.addTwoNumbers(l11, l21);
        do {
            System.out.print(listNode.val);
            listNode = listNode.next;
        } while (listNode != null);

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        StringBuilder l1Str = new StringBuilder();
        ListNode p1 = l1;
        do {
            l1Str.append(p1.val);
            p1 = p1.next;
        } while (p1 != null);

        StringBuilder l2Str = new StringBuilder();
        ListNode p2 = l2;
        do {
            l2Str.append(p2.val);
            p2 = p2.next;
        } while (p2 != null);

        int l1Length = l1Str.toString().length();
        int l2Length = l2Str.toString().length();

        String maxStr = l1Length >= l2Length ? l1Str.toString() : l2Str.toString();
        String minStr = l1Length < l2Length ? l1Str.toString() : l2Str.toString();
        String[] maxChars = new String[maxStr.length()];
        for (int i = 0; i < maxChars.length; i++) {
            maxChars[i] = String.valueOf(maxStr.charAt(i));
        }
        String[] minChars = new String[minStr.length()];
        for (int i = 0; i < minChars.length; i++) {
            minChars[i] = String.valueOf(minStr.charAt(i));
        }

        List<Integer> list = new ArrayList<>();
        int[] array = new int[maxChars.length + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = 0;
        }
        for (int i = 0; i < maxChars.length; i++) {
            if (i < minChars.length) {
                int num = Integer.valueOf(maxChars[i]) + Integer.valueOf(minChars[i]) + array[i];
                if (num >= 10) {
                    list.add(num - 10);
                    array[i + 1] = 1;
                    continue;
                }
                list.add(num);
                continue;
            }
            int num = Integer.valueOf(maxChars[i]) + array[i];
            if (num >= 10) {
                list.add(num - 10);
                array[i + 1] = 1;
                continue;
            }
            list.add(num);
        }
        if (array[maxChars.length] == 1) {
            list.add(1);
        }

        List<ListNode> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ListNode listNode = new ListNode(list.get(i), null);
            lists.add(listNode);
        }
        for (int i = 0; i < lists.size() - 1; i++) {
            lists.get(i).next = lists.get(i + 1);
        }
        return lists.get(0);
    }

    public ListNode addTwoNumbersNew(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null) {
            l1 = new ListNode(0);
        }
        if (l2 == null) {
            l2 = new ListNode(0);
        }

        int sum = l1.val + l2.val;
        if (sum > 9) {
            if (l1.next == null) {
                l1.next = new ListNode(0);
            }
            l1.next.val++;
            return new ListNode(sum % 10, addTwoNumbersNew(l1.next, l2.next));
        }
        return new ListNode(sum, addTwoNumbersNew(l1.next, l2.next));
    }

    /**
     * Definition for singly-linked list.
     */
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) { this.val = val; }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
