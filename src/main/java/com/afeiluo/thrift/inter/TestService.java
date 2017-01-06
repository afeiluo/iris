package com.afeiluo.thrift.inter;

/**
 * 使用 maven插件 swift:generate 来生成
 */
import com.facebook.swift.codec.*;
import com.facebook.swift.service.*;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.*;
import java.util.*;

@ThriftService("TestService")
public interface TestService {
    @ThriftService("TestService")
    public interface Async {
        @ThriftMethod(value = "ping")
        ListenableFuture<Long> ping();

        @ThriftMethod(value = "findPerson")
        ListenableFuture<Person> findPerson(@ThriftField(value = 1, name = "uid") final long uid);
    }

    @ThriftMethod(value = "ping")
    long ping() throws Exception;

    @ThriftMethod(value = "findPerson")
    Person findPerson(@ThriftField(value = 1, name = "uid") final long uid) throws Exception;
}