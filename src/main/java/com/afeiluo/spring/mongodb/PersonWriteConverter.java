package com.afeiluo.spring.mongodb;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@WritingConverter
public class PersonWriteConverter implements Converter<Person, DBObject> {

    public DBObject convert(Person source) {
        DBObject dbo = new BasicDBObject();
        dbo.put("_id", source.getId());
        dbo.put("name", source.getName());
        dbo.put("age", source.getAge());
        return dbo;
    }
}