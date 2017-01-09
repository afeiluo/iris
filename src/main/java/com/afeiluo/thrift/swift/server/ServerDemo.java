package com.afeiluo.thrift.swift.server;

import com.facebook.swift.service.ThriftServer;

public class ServerDemo {

    public static void main(String[] args) {
        final ServerCreator serverCreator = new ServerCreator().invoke();
        final ThriftServer server = serverCreator.getServer();
        server.start();
        System.out.println("服务已启动!");
        Runtime.getRuntime().addShutdownHook(new Thread() {// 增加虚拟机down的时候的钩子
                    @Override
                    public void run() {
                        server.close();
                        serverCreator.stop();
                    }
                });
    }
}
