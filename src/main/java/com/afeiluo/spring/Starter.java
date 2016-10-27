package com.afeiluo.spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Starter {
    private static String[] resource = new String[] { "classpath*:spring/appContext.xml" };
    private static AbstractApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext(resource);
        context.start();
        context.registerShutdownHook();
    }
}
