package com.afeiluo.zookeeper;

import com.google.common.collect.Lists;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.curator.test.TestingServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class LeaderSelectorExample {
    private static final int CLIENT_QTY = 10;

    private static final String PATH = "/examples/leader";
    private static final String CONNECT_STRING = "172.26.40.6:2181,172.26.40.6:2182,172.26.40.6:2183";

    public static void main(String[] args) throws Exception {
        // all of the useful sample code is in ExampleClient.java

        System.out
                .println("Create "
                        + CLIENT_QTY
                        + " clients, have each negotiate for leadership and then wait a random number of seconds before letting another leader election occur.");
        System.out.println("Notice that leader election is fair: all clients will become leader and will do so the same number of times.");

        List<CuratorFramework> clients = Lists.newArrayList();
        List<SelectExampleClient> examples = Lists.newArrayList();
        // TestingServer server = new TestingServer();
        try {
            for (int i = 0; i < CLIENT_QTY; ++i) {
                CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT_STRING, new ExponentialBackoffRetry(1000, 3));
                clients.add(client);

                SelectExampleClient example = new SelectExampleClient(client, PATH, "Client #" + i);
                examples.add(example);

                client.start();
                example.start();
            }

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } finally {
            System.out.println("Shutting down...");

            for (SelectExampleClient exampleClient : examples) {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }

            // CloseableUtils.closeQuietly(server);
        }
    }
}