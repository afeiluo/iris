package com.afeiluo.zookeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 当一个client的会话中断的时候会触发一次选举
 */
public class LeaderLatchExample {
    private static final String CONNECT_STRING = "172.26.40.6:2181,172.26.40.6:2182,172.26.40.6:2183";

    public static void main(String[] args) throws Exception {

        // TestingServer server = new TestingServer();

        try {
            for (int i = 0; i < 10; i++) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            final CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT_STRING, new ExponentialBackoffRetry(20000, 3));
                            final LeaderLatch leader = new LeaderLatch(client, "/francis/leader");
                            leader.addListener(new LeaderLatchListener() {
                                @Override
                                public void isLeader() {
                                    try {
                                        System.out.println(" client " + Thread.currentThread().getId() + " : I am Leader");
                                        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                                        leader.close();
                                        client.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void notLeader() {
                                    System.out.println(" client " + Thread.currentThread().getId() + " : I am not Leader");
                                }

                            });

                            client.start();
                            leader.start();
                            Thread.sleep(Integer.MAX_VALUE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }.start();

            }

        } finally {

            // for (CuratorFramework client : clients) {
            // CloseableUtils.closeQuietly(client);
            // }
            //
            // for (LeaderLatch leader : leaders) {
            // CloseableUtils.closeQuietly(leader);
            // }
            // CloseableUtils.closeQuietly(server);
        }

        // Thread.sleep(Integer.MAX_VALUE);
    }
}
