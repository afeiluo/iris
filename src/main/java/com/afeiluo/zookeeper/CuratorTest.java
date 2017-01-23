package com.afeiluo.zookeeper;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryLoop;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Assert;
import org.junit.Test;

public class CuratorTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            CuratorFramework client = ClientFactory.newClient();
            client.start();

            CuratorListener listener = new CuratorListener() {
                @Override
                public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                    System.out.println("event:" + event.getType() + " happend");
                }
            };
            client.getCuratorListenable().addListener(listener);
            Stat stat = client.checkExists().inBackground().forPath("/curatorTest");
            if (stat == null) {// 说明这个节点不存在
                System.out.print("/curatorTest not exist");
                client.create().inBackground().forPath("/curatorTest", "test".getBytes());
            }
            client.getData().watched().inBackground().forPath("/curatorTest");
            client.delete().inBackground().forPath("/curatorTest");
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExpiredSession() throws Exception {
        // see http://wiki.apache.org/hadoop/ZooKeeper/FAQ#A4
        final String connectionString = "172.26.40.6:2181,172.26.40.6:2182,172.26.40.6:2183";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        final CountDownLatch latch = new CountDownLatch(1);
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.Expired) {
                    latch.countDown();
                }
            }
        };

        final CuratorZookeeperClient client = new CuratorZookeeperClient(connectionString, 1000, 1000, watcher, new RetryOneTime(2));
        client.start();
        try {
            final AtomicBoolean firstTime = new AtomicBoolean(true);
            RetryLoop.callWithRetry(client, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    if (firstTime.compareAndSet(true, false)) {
                        try {
                            client.getZooKeeper().create("/foo", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        } catch (KeeperException.NodeExistsException ignore) {
                            // ignore
                        }
                        KillSession.kill(client.getZooKeeper(), connectionString);
                        latch.await();
                    }
                    ZooKeeper zooKeeper = client.getZooKeeper();
                    client.blockUntilConnectedOrTimedOut();
                    Assert.assertNotNull(zooKeeper.exists("/foo", false));
                    return null;
                }
            });
        } finally {
            client.close();
        }
    }
}
