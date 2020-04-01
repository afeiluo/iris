package com.afeiluo.springboot.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * https://blog.csdn.net/u010963948/article/details/83507185
 * Created by qiaolinfei on 2020/3/26.
 */
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 监听事件变化的回调方法
     * 可以定义自己的event，然后在业务代码里面发布event
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("我的父容器为：" + event.getApplicationContext().getParent());
        System.out.println("初始化时我被调用了。");
    }
}
