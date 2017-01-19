package com.afeiluo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Ignore;
import org.junit.Test;

/**
 */
public class AppTest {

    @Test
    public void testInterrupt() throws InterruptedException {
        ThreadA ta = new ThreadA();
        ta.setName("ThreadA");
        ta.start();
        Thread.sleep(2010);
        System.out.println(ta.getName() + "正在被中断...");
        ta.interrupt();
        System.out.println("ta.isInterrupted()=" + ta.isInterrupted());
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        queue.take();
    }

    @Ignore
    @Test
    public void testInterrupt2() throws Exception {
        // 将任务交给一个线程执行
        Thread t = new Thread(new ATask());
        t.start();
        // 运行一断时间中断线程
        Thread.sleep(90);
        System.out.println("****************************");
        System.out.println("Interrupted Thread!");
        System.out.println("****************************");
        t.interrupt();
    }
}

class ThreadA extends Thread {
    int count = 0;

    public void run() {
        System.out.println(getName() + "将要运行...");
        while (!this.isInterrupted()) {
            System.out.println(getName() + "运行中" + count++);
            try {
                Thread.sleep(400);// interrupt()后阻塞状态取消,中断状态为置位(说白了就是一直在轮询interrupted()) block-->unblock
            } catch (InterruptedException e) {
                System.out.println(getName() + "从阻塞中退出...");
                System.out.println("this.isInterrupted()=" + this.isInterrupted());
                // Thread.currentThread().interrupt();
            }
        }
        System.out.println(getName() + "已经终止!");
    }

}

class ATask implements Runnable {

    private double d = 0.0;

    public void run() {

        try {
            // 检查程序是否发生中断
            while (!Thread.interrupted()) {
                System.out.println("I am running!");
                // point1 before sleep
                Thread.sleep(20);
                // point2 after sleep
                System.out.println("Calculating");
                for (int i = 0; i < 900000; i++) {
                    d = d + (Math.PI + Math.E) / d;
                }
            }

        } catch (InterruptedException e) {
            System.out.println("Exiting by Exception");
        }

        System.out.println("ATask.run() interrupted!");
    }
}