package com.afeiluo.spring.mongodb;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Repository;

/**
 * 监听回调 DBObject--> java object
 * 
 * @author qiaolinfei
 * 
 */
@Repository
public class BeforeConvertListener extends AbstractMongoEventListener<Person> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<Person> event) {
        // ... does some auditing manipulation, set timestamps, whatever ...
        System.out.println("~onBeforeConvert~");
    }
}
