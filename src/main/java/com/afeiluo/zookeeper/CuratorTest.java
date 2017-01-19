package com.afeiluo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.data.Stat;

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
}
