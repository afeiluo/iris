package com.afeiluo.thrift.server;

import com.facebook.swift.service.ThriftServer;

public class ServerDemo {

    public static void main(String[] args) {
        ServerCreator serverCreator = new ServerCreator().invoke();
        ThriftServer server = serverCreator.getServer();
        server.start();
        System.out.println("服务已启动!");
    }

}
