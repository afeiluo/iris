package com.afeiluo.springboot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by qiaolinfei on 2020/3/27.
 */
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
        System.out.println("this is MySpringApplicationRunListener");
    }

    @Override
    public void starting() {
        System.out.println("starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("running");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("failed");
    }
}
