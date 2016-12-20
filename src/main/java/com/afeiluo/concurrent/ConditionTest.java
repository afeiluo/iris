package com.afeiluo.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用Lock 和  Condition 来演示 线程之间的同步(通信)
 * main和sub线程交替执行(println)--->同步的目的:一个线程执行完了告诉另一个线程执行
 * @author qiaolinfei
 *Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用(wait,notify,notifyAll)
 */
public class ConditionTest {
    public static void main(String[] args) {
        final Business business = new Business();//两个线程(main sub)通过Business对象来进行通信
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadExecute(business, "sub");
            }
        }).start();
        threadExecute(business, "main");
    }

    public static void threadExecute(Business business, String threadType) {
        for (int i = 0; i < 10; i++) {//main 和 sub各执行10次
            try {
                if ("main".equals(threadType)) {
                    business.main(i);
                } else {
                    business.sub(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Business {
    private boolean bool = true;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void main(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (bool) {
                condition.await();// this.wait(); main线程阻塞
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("main thread seq of " + i + ", loop of " + loop);
            }
            bool = true;//执行完了之后 阻塞自己
            condition.signal();// this.notify(); 唤醒阻塞的sub线程
        } finally {
            lock.unlock();
        }
    }

    public void sub(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (!bool) {
                condition.await();// this.wait(); sub线程阻塞
            }
            for (int i = 0; i < 10; i++) {
                System.out.println("sub thread seq of " + i + ", loop of " + loop);
            }
            bool = false;//执行完了之后 阻塞自己
            condition.signal();// this.notify(); 唤醒阻塞的main线程
        } finally {
            lock.unlock();
        }
    }
}