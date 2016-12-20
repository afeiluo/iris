package com.afeiluo.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 自定义的事件监听器(无序)
 * 
 * @author qiaolinfei
 * 
 */
@Component
public class AsyncContentListener implements ApplicationListener<ApplicationEvent> {

    @Async
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContentEvent) {// 如果是自定义的时间
            System.out.println("async listener收到的消息是:" + event.getSource());

        }
    }
}
