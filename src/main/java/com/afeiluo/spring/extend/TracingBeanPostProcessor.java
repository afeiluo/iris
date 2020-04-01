package com.afeiluo.spring.extend;

/**
 * Created by qiaolinfei on 2020/3/31.
 */

import org.springframework.beans.factory.config.BeanPostProcessor;

public class TracingBeanPostProcessor implements BeanPostProcessor {

    // simply return the instantiated bean as-is
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean; // we could potentially return any object reference here...
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("Bean '" + beanName + "' created : " + bean.toString());
        return bean;
    }
}