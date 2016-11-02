package com.afeiluo.algorithm;


import java.util.Scanner;

/**
 * 递归算法实现汉诺塔
 * <p>
 * 输出汉诺塔问题中  n个盘子 第m步移动的详细情况
 */
public class Hanota {

    /**
     * 定义移动次数
     */
    private static int count;
    private static int m;

    /**
     * 设置移动次数的起始值
     */
    public static void setCount() {
        count = 1;
    }


    /**
     * 移动递归
     *
     * @param num   盘子数
     * @param from  柱子A
     * @param inner 柱子B
     * @param to    柱子C
     */
    public static void moveDish(int num, char from, char inner, char to) {

        if (num == 1) {
            if (count == m) {
                System.out.println(num + " " + -(64 - from) + " " + -(64 - to));//输出第m步 第num个盘子从from移动到to

            }
            System.out.println(count+" "+num + " " +  from + "-->" + to);
            count++;

        } else {//后一步是前一步的 *2+1
            moveDish(num - 1, from, to, inner);//把前num-1个当成一个整体从 A-->B
            if (count == m) {
                System.out.println(num + " " + -(64 - from) + " " + -(64 - to));//把第num个从 A-->C

            }
            System.out.println(count+" "+num + " " +  from + "-->" + to);
            count++;
            moveDish(num - 1, inner, from, to);//把前num-1个当前一个真题从 B-->C
        }
    }

    public static void main(String[] args) {
        for (; ; ) {
            Scanner sc = new Scanner(System.in);
            try {
                int n = sc.nextInt();//盘子数量
                m = sc.nextInt();//步骤数
                setCount();
                moveDish(n, 'A', 'B', 'C');
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
