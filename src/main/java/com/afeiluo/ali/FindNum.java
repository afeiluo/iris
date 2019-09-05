package com.afeiluo.ali;


/**
 * 实现一个时间复杂度为O(n)的算法，在存储一系列整数的数组中找出最大最小的两个。
 *
 * @author ben
 * @date 2019/9/5
 */
public class FindNum {

    /**
     * 将最大、最小值设置为数组第一个元素，然后遍历数组和最大、最小值比较
     *
     * @param arr
     */
    public void find(int[] arr) {
        Integer len = arr.length;
        Integer min = arr[0];
        Integer max = arr[0];
        for (int i = 0; i < len; i++) {
            if (arr[i] >= max) {
                max = arr[i];
            } else if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("max: " + max + " min:" + min);
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 12, -1, 43, 5, 34, 100};
        FindNum findNum = new FindNum();
        findNum.find(arr);
    }
}
