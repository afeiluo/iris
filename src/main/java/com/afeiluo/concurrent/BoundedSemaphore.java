package com.afeiluo.concurrent;

/**
 * 带上限的信号量
 * 当上限bound为1的时候可以当成锁来用
 * 
 * @author qiaolinfei
 * 
 */
public class BoundedSemaphore {
    private int signals = 0;
    private int bound = 0;

    public BoundedSemaphore(int upperBound) {
        this.bound = upperBound;
    }

    public synchronized void take() throws InterruptedException {
        while (this.signals == bound)
            wait();
        this.signals++;
        this.notify();
    }

    public synchronized void release() throws InterruptedException {
        while (this.signals == 0)
            wait();
        this.signals--;
        this.notify();
    }

}