package com.afeiluo.thread;

/**
 * Created by qiaolinfei on 2020/7/12.
 */
public class ThreadLocalTest {
    private static ThreadLocal threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        threadLocal.set("s");
        System.out.println(threadLocal.get());
    }
}
