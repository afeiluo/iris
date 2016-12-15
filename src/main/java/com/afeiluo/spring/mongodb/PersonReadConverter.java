package com.afeiluo.spring.mongodb;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import com.mongodb.DBObject;

@ReadingConverter
public class PersonReadConverter implements Converter<DBObject, Person> {

    public Person convert(DBObject source) {
        Person p = new Person(((ObjectId) source.get("_id")).toHexString(), (Integer) source.get("age"));
        return p;
    }
}