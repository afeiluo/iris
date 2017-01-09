package com.afeiluo.thrift.swift.client;

import static com.google.common.net.HostAndPort.fromParts;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Nullable;

import com.afeiluo.thrift.swift.inter.Person;
import com.afeiluo.thrift.swift.inter.TestService;
import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public class ClientDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("outer thread:" + Thread.currentThread().getId());
        final ThriftClientManager clientManager = new ThriftClientManager();

        // 异步的调用方式
        TestService.Async testService = clientManager.createClient(new FramedClientConnector(fromParts("localhost", 12345)), TestService.Async.class)
                .get();
        ListenableFuture<Person> future = testService.findPerson(111L);
        final CountDownLatch latch = new CountDownLatch(1);
        Futures.addCallback(future, new FutureCallback<Person>() {

            @Override
            public void onSuccess(@Nullable Person result) {
                System.out.println("inner thread:" + Thread.currentThread().getId());
                System.out.println("success:" + result);
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("inner thread:" + Thread.currentThread().getId());
                System.out.println("failed");
                latch.countDown();
            }
        });
        latch.await();
        clientManager.close();

        // 同步的调用方式
        // TestService testService = clientManager.createClient(new FramedClientConnector(fromParts("localhost",
        // 12345)), TestService.class).get();
        // System.out.println(testService.findPerson(111L));
        // int max = 100000;
        // Long start = System.currentTimeMillis();
        // for (int i = 0; i < max; i++) {
        // testService.findPerson(111L);
        // }
        // Long end = System.currentTimeMillis();
        // Long elapse = end - start;
        // int perform = Double.valueOf(max / (elapse / 1000d)).intValue();
        //
        // System.out.print("thrift " + max + " 次RPC调用，耗时：" + elapse + "毫秒，平均" + perform + "次/秒");
        // clientManager.close();
    }
}
