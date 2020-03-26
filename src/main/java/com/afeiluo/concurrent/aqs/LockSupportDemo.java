package com.afeiluo.concurrent.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by qiaolinfei on 2020/3/24.
 */
public class LockSupportDemo {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                LockSupport.park();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(getName() + " 被中断了");
                }
                System.out.println(getName() + " 继续执行");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start(); //拿到 u 的对象锁，同时被 park 阻塞了
        Thread.sleep(1000L);
        t2.start(); // 拿不到 u 的对象锁, 进入阻塞队列
        Thread.sleep(3000L);
        t1.interrupt(); // 从 park 阻塞中中断出来
        LockSupport.unpark(t2);
        t1.join(); // 等待 t1 执行结束
        t2.join(); // 等待 t2 执行结束
        System.out.println("结束了");
    }
}
