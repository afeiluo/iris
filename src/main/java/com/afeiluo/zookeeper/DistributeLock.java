package com.afeiluo.zookeeper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * 使用zookeeper来实现分布式锁的demo
 * 
 * @author qiaolinfei
 * 
 */
public class DistributeLock {

    private static final int SESSION_TIMEOUT = 5000;// 客户端连接超时的时间
    private String hosts = "172.26.40.6:2181,172.26.40.6:2182,172.26.40.6:2183";// 自己的zk集群
    private String groupNode = "father";// 根节点
    private String subNode = "son";// 子节点

    private ZooKeeper zk;
    private String thisPath;// 自己的节点路径
    private String waitPath;// 获取锁的要监听的节点路径

    private CountDownLatch latch = new CountDownLatch(1);// 用来线程同步

    public synchronized void connectZookeeper() throws Exception {
        zk = new ZooKeeper(hosts, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                try {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                    if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)) {// 当自己监听的节点被删除的时候就认为自己获得了锁
                        doSomething();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        latch.await();// 已经成功连接zk
        if (zk.exists("/" + groupNode, true) == null) {// 父节点不存在先创建父节点
            zk.create("/" + groupNode, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        thisPath = zk.create("/" + groupNode + "/" + subNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);// 设置自己的节点
                                                                                                                                  // 主要后面的createMode
        Thread.sleep(10);
        List<String> childrenNodes = zk.getChildren("/" + groupNode, false);// 获取父节点下面的所有子节点
        if (childrenNodes.size() == 1) {// 列表中只有一个节点那肯定就是thisPath 说明client获得了锁
            doSomething();
        } else {
            String thisNode = this.thisPath.substring(("/" + groupNode + "/").length());
            Collections.sort(childrenNodes);
            int index = childrenNodes.indexOf(thisNode);
            if (index == -1) {

            } else if (index == 0) {
                // index=0 说明thisNode在列表中最下,当前client获得锁
                doSomething();
            } else {
                this.waitPath = "/" + groupNode + "/" + childrenNodes.get(index - 1);// 自己前面的那个node就是自己要监听的
                // 在waitPath上注册监听器,当waitPath被删除时,zookeeper会回调监听器的process方法
                zk.getData(waitPath, true, new Stat());
            }

        }
    }

    // 获得锁之后的处理
    private void doSomething() throws Exception {
        try {
            System.out.println("gain aqs:" + thisPath);
            Thread.sleep(2000);
        } finally {
            System.out.println("finished:" + thisPath);
           // zk.delete(this.thisPath, -1);// 使用完锁之后删掉自己的znode
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        DistributeLock tzk = new DistributeLock();
                        tzk.connectZookeeper();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            }.start();
        }
        Thread.sleep(Long.MAX_VALUE);
    }

}
