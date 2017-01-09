package com.afeiluo.thrift.swift.inter;

/**
 * 使用 maven插件 swift:generate 来生成
 */
import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.google.common.util.concurrent.ListenableFuture;

@ThriftService("TestService")
public interface TestService extends AutoCloseable {
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