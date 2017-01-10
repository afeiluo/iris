package com.afeiluo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ClientFactory {

    public static CuratorFramework newClient() {
        String connectionString = "172.26.40.6:2181,172.26.40.6:2182,172.26.40.6:2183";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }
}
