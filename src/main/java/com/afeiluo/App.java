package com.afeiluo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * Hello world!
 * 
 */
public class App {
    public static void main(String[] args) {
        testSort();
    }

    public static void testTryCatchFinally() {
        try {

            try {
                throw new RuntimeException("this is a runtime exception");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            } finally {
                System.out.println("this is inner finally block");
            }
        } finally {
            System.out.println("this is outer finally block");
        }
    }

    public static void testStr() {
        String province = "\"四川省\"";
        System.out.println("origin:" + province);
        province = StringUtils.removeStart(province, "\"");
        province = StringUtils.removeEnd(province, "\"");
        System.out.println("after:" + province);
        // System.out.println(StringUtils.replace(province, "省", ""));
    }

    public static void testSort() {
        List<Integer> list = Lists.newArrayList();
        list.add(12);
        list.add(2);
        list.add(100);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        System.out.println(list);
    }
}
