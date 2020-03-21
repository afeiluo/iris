package com.afeiluo.threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ben
 * @date 2019/8/7
 * https://blog.csdn.net/qq_25806863/article/details/71126867
 */
public class ThreadPoolExecutorExample {


    public static void main(String[] args) {
        /**
         * 任务是休眠两秒然后打印线程名称
         */
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " run");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        /**
         * 核心线程6 最大线程数为10 超时时间5秒
         */
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 10, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        /**
         * 核心线程数为3，最大线程数为6。超时时间为5秒,队列是LinkedBlockingDeque
         */
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        /**
         * 核心线程数为3，最大线程数为6。超时时间为5秒,队列是SynchronousQueue
         */
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 6, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        /**
         * 核心线程数是3，最大线程数是4，队列是LinkedBlockingDeque LinkedBlockingDeque根本不受最大线程数影响。
         */
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

        /**
         * 当LinkedBlockingDeque有大小限制时就会受最大线程数影响了
         */
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(2));
        /**
         * 队列大小调为1
         */
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1));

        /**
         * 核心线程数是3 ，最大线程数是4，队列是SynchronousQueue
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        executor.execute(myRunnable);
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        System.out.println("---先开三个---");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        executor.execute(myRunnable);
        System.out.println("---再开三个---");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----8秒之后----");
        System.out.println("核心线程数" + executor.getCorePoolSize());
        System.out.println("线程池数" + executor.getPoolSize());
        System.out.println("队列任务数" + executor.getQueue().size());
    }
}
