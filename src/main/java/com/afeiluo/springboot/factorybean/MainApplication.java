package com.afeiluo.springboot.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by qiaolinfei on 2020/3/27.
 */
public class MainApplication {
    public static void main(String[] args) {
        // 通过 @Configuration @ComponentScan 注解来启动上下文
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("容器启动完成");
        UserService userService = applicationContext.getBean(UserService.class);
        System.out.println(userService);
        Object customerFactoryBean = applicationContext.getBean("customerFactoryBean");
        System.out.println(customerFactoryBean);
        Object realCustomerFactoryBean = applicationContext.getBean("&customerFactoryBean");
        System.out.println(realCustomerFactoryBean);
    }

}
