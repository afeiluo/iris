package com.afeiluo.ali;


import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 实现一段死锁的代码
 *
 * @author ben
 * @date 2019/9/5
 */
public class DeadLock {
    public static void main(String[] args) {
        DeadTask deadTask = new DeadTask();
        Thread thread1 = new Thread(deadTask);
        Thread thread2 = new Thread(deadTask);
        thread1.start();
        thread2.start();

    }
}


class DeadTask implements Runnable {

    private Object lock1 = new Object();

    private Object lock2 = new Object();

    //控制不同的线程优先获取不同的锁
    private AtomicBoolean flag = new AtomicBoolean(true);


    @Override
    public void run() {
        if (flag.getAndSet(false)) {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " get lock1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " get lock2");
                }
            }
        } else {
            flag.set(true);
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " get lock2");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + " get lock1");
                }
            }
        }

    }
}