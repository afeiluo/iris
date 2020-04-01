package com.afeiluo.springboot.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by qiaolinfei on 2020/3/26.
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        // 打印容器里面有多少个bean
        System.out.println("bean count=====" + configurableApplicationContext.getBeanDefinitionCount());
        // 打印所有 beanName
        System.out.println(configurableApplicationContext.getBeanDefinitionCount() + "个Bean的名字如下：");
        String[] beanDefinitionNames = configurableApplicationContext.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            System.out.println(beanName);
        }
    }
}
