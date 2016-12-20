package com.afeiluo.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 有序的监听器
 * 
 * @author qiaolinfei
 * 
 */
@Component
public class SmartContentListener implements SmartApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("SmartContentListener收到的消息:" + event.getSource());
    }

    /**
     * 接受的顺序
     */
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ContentEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == String.class;
    }

}
