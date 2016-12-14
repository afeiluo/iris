package com.afeiluo.spring.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoDbService {

    @Autowired
    @Qualifier("anotherMongoTemplate")
    private MongoTemplate mongoTemplate;

    public void insert() {
        Person p = new Person("ben", 34);
        ((MongoOperations) mongoTemplate).insert(p);
    }
}
