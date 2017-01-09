package com.afeiluo.thrift.origin.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import com.afeiluo.thrift.origin.inter.TestService;
import com.afeiluo.thrift.origin.inter.impl.TestServiceImpl;

public class ServerDemo {

    public static final int SERVER_PORT = 8090;

    public TServer startServer() {
        try {
            System.out.println("ServerDemo start ....");
            TProcessor tprocessor = new TestService.Processor<TestServiceImpl>(new TestServiceImpl());

            // 简单的单线程服务模型，一般用于测试
            // TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            // TServer.Args tArgs = new TServer.Args(serverTransport);
            // tArgs.processor(tprocessor);
            // tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // // tArgs.protocolFactory(new TCompactProtocol.Factory());
            // // tArgs.protocolFactory(new TJSONProtocol.Factory());
            // TServer server = new TSimpleServer(tArgs);

            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            // TServer server = new TThreadPoolServer(new
            // TThreadPoolServer.Args(serverTransport).processor(tprocessor));

            // 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式。
            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());
            TServer server = new TNonblockingServer(tnbArgs);

            // 半同步半异步的服务端模型，需要指定为： TFramedTransport 数据传输的方式。
            // TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
            // SERVER_PORT);
            // THsHaServer.Args thhsArgs = new THsHaServer.Args(tnbSocketTransport);
            // thhsArgs.processor(tprocessor);
            // thhsArgs.transportFactory(new TFramedTransport.Factory());
            // thhsArgs.protocolFactory(new TBinaryProtocol.Factory());
            // TServer server = new THsHaServer(thhsArgs);

            server.serve();
            return server;
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ServerDemo server = new ServerDemo();
        final TServer tserver = server.startServer();
        Runtime.getRuntime().addShutdownHook(new Thread() {// 增加虚拟机down的时候的钩子
                    @Override
                    public void run() {
                        if (tserver != null) {
                            tserver.stop();
                        }
                    }
                });
    }
}
