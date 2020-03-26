package com.afeiluo.thrift.swift.server;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newFixedThreadPool;

import java.util.concurrent.ExecutorService;


import com.afeiluo.thrift.swift.inter.impl.TestServiceImpl;
import com.facebook.nifty.core.NettyServerConfig;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.google.common.collect.ImmutableList;
import org.springframework.util.Assert;

/**
 */
public class ServerCreator {
    private ExecutorService taskWorkerExecutor;
    private ThriftServer server;
    private ExecutorService bossExecutor;
    private ExecutorService ioWorkerExecutor;

    public ThriftServer getServer() {
        return server;
    }

    public ServerCreator invoke(int port) {
        ThriftServiceProcessor processor = new ThriftServiceProcessor(new ThriftCodecManager(), ImmutableList.<ThriftEventHandler>of(),
                new TestServiceImpl());

        taskWorkerExecutor = newFixedThreadPool(1);

        ThriftServerDef serverDef = ThriftServerDef.newBuilder().listen(port).withProcessor(processor).using(taskWorkerExecutor).build();

        bossExecutor = newCachedThreadPool();
        ioWorkerExecutor = newCachedThreadPool();

        NettyServerConfig serverConfig = NettyServerConfig.newBuilder().setBossThreadExecutor(bossExecutor).setWorkerThreadExecutor(ioWorkerExecutor)
                .build();

        server = new ThriftServer(serverConfig, serverDef);
        return this;
    }

    public void checkExecutorsTerminated() {
        Assert.isTrue(bossExecutor.isTerminated());
        Assert.isTrue(ioWorkerExecutor.isTerminated());
        Assert.isTrue(taskWorkerExecutor.isTerminated());
    }

    public void stop() {
        server.close();
    }
}
