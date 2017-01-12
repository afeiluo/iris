package com.afeiluo.spring.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class MongoDbService {

    @Autowired
    @Qualifier("anotherMongoTemplate")
    private MongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(MongoDbService.class);

    public void curd() {
        Person p = new Person("ben", 34);
        MongoOperations mongoOps = (MongoOperations) mongoTemplate;// MongoTemplate 是MongoOperations的实现
        mongoOps.insert(p);// 会生成一个叫做person的collection 除了person的属性会在collection中映射成响应的字段外(Person
                           // 里面的id属性会被映射到collection中的_id)，还会增加 "_class" :
                           // "com.afeiluo.spring.mongodb.Person" 字段

        // Find
        p = mongoOps.findById(p.getId(), Person.class);
        log.info("Found: " + p);

        // Update
        mongoOps.updateFirst(query(where("name").is("ben")), update("age", 35), Person.class);
        p = mongoOps.findOne(query(where("name").is("ben")), Person.class);
        log.info("Updated: " + p);

        // Delete
        mongoOps.remove(p);

        // Check that deletion worked
        List<Person> people = mongoOps.findAll(Person.class);
        log.info("Number of people = : " + people.size());

        mongoOps.dropCollection(Person.class);
    }

    public void findAndUpdate() {
        mongoTemplate.insert(new Person("Tom", 21));
        mongoTemplate.insert(new Person("Dick", 22));
        mongoTemplate.insert(new Person("Harry", 23));

        Query query = new Query(Criteria.where("name").is("Harry"));
        Update update = new Update().inc("age", 1);
        Person p = mongoTemplate.findAndModify(query, update, Person.class); // return's old person object

        p = mongoTemplate.findOne(query, Person.class);

        // Now return the newly updated document when updating
        p = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Person.class);
        mongoTemplate.dropCollection(Person.class);
    }
}
