package com.afeiluo.spring.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;

public class MongoApp {
    private static final Log log = LogFactory.getLog(MongoApp.class);

    public static void main(String[] args) throws Exception {
        UserCredentials uc = new UserCredentials("user2", "666789");
        MongoOperations mongoOps = new MongoTemplate(new Mongo("172.26.40.6", 27017), "test_tech", uc);
        mongoOps.insert(new Person("Joe", 34));
        log.info(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));
        // mongoOps.dropCollection("person");
    }
}
