package com.afeiluo.thrift.swift.inter;

import static com.google.common.base.Objects.toStringHelper;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct("Person")
public class Person {
    public Person() {
    }

    private String name;

    @ThriftField(value = 1, name = "name")
    public String getName() {
        return name;
    }

    @ThriftField
    public void setName(final String name) {
        this.name = name;
    }

    private int age;

    @ThriftField(value = 2, name = "age")
    public int getAge() {
        return age;
    }

    @ThriftField
    public void setAge(final int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("name", name).add("age", age).toString();
    }
}
