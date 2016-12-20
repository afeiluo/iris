package com.afeiluo.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * 自定义一个事件
 * 
 * @author qiaolinfei
 * 
 */
public class ContentEvent extends ApplicationEvent {
    public ContentEvent(final String source) {
        super(source);
    }

}
