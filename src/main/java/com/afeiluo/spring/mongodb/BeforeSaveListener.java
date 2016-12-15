package com.afeiluo.spring.mongodb;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Repository;

/**
 * 监听回调 java object --> DBObject
 * 
 * @author qiaolinfei
 * 
 */
@Repository
public class BeforeSaveListener extends AbstractMongoEventListener<Person> {
    @Override
    public void onBeforeSave(BeforeSaveEvent<Person> event) {
        // … change values, delete them, whatever …
        System.out.println("~onBeforeSave~");
    }
}
