package com.afeiluo.thrift.origin.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import com.afeiluo.thrift.origin.inter.TestService;
import com.afeiluo.thrift.origin.inter.TestService.AsyncClient;
import com.afeiluo.thrift.origin.inter.TestService.AsyncClient.findPerson_call;

/**
 * 异步客户端
 * 
 * @author qiaolinfei
 * 
 */
public class AsyncClientDemo {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

    /**
     * 
     * @param userName
     */
    public void startClient(String userName) {
        try {
            TAsyncClientManager clientManager = new TAsyncClientManager();
            TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocolFactory tprotocol = new TCompactProtocol.Factory();
            TestService.AsyncClient asyncClient = new TestService.AsyncClient(tprotocol, clientManager, transport);
            System.out.println("Client start .....");

            CountDownLatch latch = new CountDownLatch(1);
            AsynCallback callBack = new AsynCallback(latch);
            System.out.println("call method findPerson start ...");
            asyncClient.findPerson(111L, callBack);
            System.out.println("call method findPerson .... end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            System.out.println("latch.await =:" + wait);
            transport.close();// 注意要close否则server会报强制中断连接的异常
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("startClient end.");
    }

    public class AsynCallback implements AsyncMethodCallback<AsyncClient.findPerson_call> {
        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onError(Exception exception) {
            System.out.println("onError :" + exception.getMessage());
            latch.countDown();
        }

        @Override
        public void onComplete(findPerson_call response) {
            try {
                System.out.println(response.getResult().toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        AsyncClientDemo client = new AsyncClientDemo();
        client.startClient("Michael");
    }

}
