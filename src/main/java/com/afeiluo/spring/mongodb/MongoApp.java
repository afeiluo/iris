package com.afeiluo.spring.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;

public class MongoApp {
    private static final Logger log = LoggerFactory.getLogger(MongoApp.class);

    public static void main(String[] args) throws Exception {
        UserCredentials uc = new UserCredentials("user2", "666789");
        MongoOperations mongoOps = new MongoTemplate(new Mongo("172.26.40.6", 27017), "test_tech", uc);
        mongoOps.insert(new Person("Joe", 34));
        mongoOps.findOne(new Query(where("name").is("Joe")), Person.class);
        // mongoOps.dropCollection("person");
    }
}
