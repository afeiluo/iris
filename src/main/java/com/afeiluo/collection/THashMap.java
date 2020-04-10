package com.afeiluo.collection;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by qiaolinfei on 2020/4/10.
 */
public class THashMap {
    static final int MAXIMUM_CAPACITY = 1 << 30;


    public static int getCapacity(HashMap<String, String> hashMap) {
        try {
            Field field = hashMap.getClass().getDeclaredField("threshold");
            field.setAccessible(true);
            return (int) field.get(hashMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(20, 0.8f);
        System.out.println("calc tables size: " + tableSizeFor(20));
        System.out.println("======================");
        System.out.println("calc tables size: " + tableSizeFor(1));
        System.out.println("======================");
        System.out.println("calc tables size: " + tableSizeFor(5));
        System.out.println("======================");
        System.out.println("calc tables size: " + tableSizeFor((1 << 20) - 111));
        System.out.println(1 << 20);
        System.out.println("======================");
        for (int i = 0; i < 100; i++) {
            map.put(String.valueOf(i), String.valueOf(i + 1));
            System.out.println("capacity: " + getCapacity(map) + " size: " + map.size());
        }
    }

    /**
     * 得到一个离cap最近的2的幂指数值
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        System.out.println(n);
        n |= n >>> 1;
        System.out.println(n);
        n |= n >>> 2;
        System.out.println(n);
        n |= n >>> 4;
        System.out.println(n);
        n |= n >>> 8;
        System.out.println(n);
        n |= n >>> 16;
        System.out.println(n);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
