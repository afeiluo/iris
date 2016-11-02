package com.afeiluo.algorithm;

import java.util.Scanner;

/**
 * Created by ben on 16/11/2.
 * <p>
 * 水仙花数问题
 * <p>
 * 例如:153=1^3+5^3+3^3
 */
public class Narcissus {
    private static int count = 0;

    /**
     * 判断s是几位数字
     *
     * @param s
     * @return
     */
    static int place(int s) {
        int i = 0;
        for (; s != 0; i++) {
            s /= 10;
        }
        return i;
    }

    static void action(int s, int e) {
        for (int i = s; i <= e; i++) {
            if (judge(i)) {
                count++;
                System.out.println(i);
            }
        }
    }

    static boolean judge(int s) {
        int sum = 0, n;
        n = place(s);
        for (int i = 0, t = s; t != 0; i++) {
            sum += Math.pow(t % 10, n);
            t /= 10;
        }
        if (s == sum) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int s = in.nextInt();//start
        int e = in.nextInt();//end
        if (e == 1 | s == e) {
            if (judge(s)) {
                count++;
                System.out.println(s);
            } else {
            }
        } else {
            action(s, e);
        }
        System.out.println("total:" + count);
    }

}
