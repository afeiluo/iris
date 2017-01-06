package com.afeiluo.thrift.inter.impl;

import com.afeiluo.thrift.inter.Person;
import com.afeiluo.thrift.inter.TestService;

public class TestServiceImpl implements TestService {

    @Override
    public long ping() throws Exception {
        return 0;
    }

    @Override
    public Person findPerson(long uid) throws Exception {
        Person p = new Person();
        p.setAge(10);
        p.setName("ddd");
        return p;
    }
}
