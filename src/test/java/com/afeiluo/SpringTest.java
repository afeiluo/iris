package com.afeiluo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.afeiluo.spring.event.ContentEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/appContext.xml" })
public class SpringTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testEvent() {
        applicationContext.publishEvent(new ContentEvent("这是一个事件"));
    }
}
