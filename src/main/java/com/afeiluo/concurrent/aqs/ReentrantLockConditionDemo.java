package com.afeiluo.concurrent.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ben
 * @date 2019/8/9
 */
public class ReentrantLockConditionDemo {
    final Lock lock = new ReentrantLock();

    final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ReentrantLockConditionDemo demo = new ReentrantLockConditionDemo();
        Producer producer = demo.new Producer();
        Consumer consumer = demo.new Consumer();
        consumer.start();
        producer.start();
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            try {
                lock.lock();
                System.out.println("我在等一个新信号" + this.currentThread().getName());
                condition.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("拿到一个信号" + this.currentThread().getName());
                lock.unlock();

            }
        }
    }

    class Producer extends Thread {
        @Override
        public void run() {
            produce();
        }

        private void produce() {
            try {
                lock.lock();
                System.out.println("我拿到锁" + this.currentThread().getName());
                condition.signalAll();
                System.out.println("我发出了一个信号：" + this.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }
    }

}
