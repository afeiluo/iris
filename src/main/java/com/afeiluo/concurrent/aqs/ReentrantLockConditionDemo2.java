package com.afeiluo.concurrent.aqs;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://blog.csdn.net/bohu83/article/details/51098106
 * @author ben
 * @date 2019/8/9
 */
public class ReentrantLockConditionDemo2 {
    private int queueSize = 10;

    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);

    private Lock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockConditionDemo2 demo = new ReentrantLockConditionDemo2();
        Producer producer = demo.new Producer();
        Consumer consumer = demo.new Consumer();
        producer.start();
        consumer.start();
        Thread.sleep(10);
        producer.interrupt();
        consumer.interrupt();

    }

    class Consumer extends Thread {
        volatile boolean flag = true;

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("队列空，等待数据");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            flag = false;
                        }
                    }
                    queue.poll();                //每次移走队首元素
                    notFull.signal();
                    System.out.println("从队列取走一个元素，队列剩余" + queue.size() + "个元素");
                } finally {
                    lock.unlock();
                }

            }
        }
    }

    class Producer extends Thread {
        volatile boolean flag = true;

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("队列满，等待有空余空间");
                            notFull.await();
                        } catch (InterruptedException e) {

                            flag = false;
                        }
                    }
                    queue.offer(1);        //每次插入一个元素
                    notEmpty.signal();
                    System.out.println("向队列取中插入一个元素，队列剩余空间：" + (queueSize - queue.size()));
                } finally {
                    lock.unlock();
                }

            }
        }

    }
}
