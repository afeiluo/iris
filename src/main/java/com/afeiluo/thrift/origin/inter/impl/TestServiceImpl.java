package com.afeiluo.thrift.origin.inter.impl;

import org.apache.thrift.TException;

import com.afeiluo.thrift.origin.inter.Person;
import com.afeiluo.thrift.origin.inter.TestService;

/**
 * http://www.micmiu.com/soa/rpc/thrift-sample/
 * 
 * @author qiaolinfei
 * 
 */
public class TestServiceImpl implements TestService.Iface {

    @Override
    public long ping() throws TException {
        return 0;
    }

    @Override
    public Person findPerson(long uid) throws TException {
        Person p = new Person();
        p.setAge(29);
        p.setName("ddd");
        return p;
    }

}
