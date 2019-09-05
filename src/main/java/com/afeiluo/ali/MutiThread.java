package com.afeiluo.ali;

/**
 * 补充如下程序通过N个线程顺序循环打印从0至100，如给定N=3则输出：
 *
 * //thread0: 0
 * //thread1: 1
 * //thread2: 2
 * //thread0: 3
 * //thread1: 4
 * //...
 * @author ben
 * @date 2019/9/5
 */
public class MutiThread {

    public static void main(String[] args) {
        int n = 10; //线程个数
        int max = 100; // 打印的数字的最大值
        Object lock = new Object();
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new Task(i, max, n, lock), "thread" + i);
            thread.start();
        }
    }
}


class Task implements Runnable {

    private Integer threadIndex; //线程编号

    private Integer max;//打印的最大值

    private Integer threadCount; // 线程的数量

    private static Integer index = 0; //开始打印的值

    private Object object;


    public Task(Integer threadIndex, Integer max, Integer threadCount, Object object) {
        this.threadIndex = threadIndex;
        this.max = max;
        this.threadCount = threadCount;
        this.object = object;
    }

    @Override
    public void run() {
        this.print();
    }

    /**
     * threadIndex 是 index % threadCount  的线程打印 index
     */
    public void print() {
        synchronized (object) {
            while (index <= max) {
                if (index % threadCount == threadIndex) {
                    System.out.println(Thread.currentThread().getName() + ":" + index);
                    index++;
                    object.notifyAll();
                } else {
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}