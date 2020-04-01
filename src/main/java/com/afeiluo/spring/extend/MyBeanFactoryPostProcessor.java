package com.afeiluo.spring.extend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Created by qiaolinfei on 2020/3/31.
 * BeanFactoryPostProcessor接口实现类可以在当前BeanFactory初始化后，bean实例化之前对BeanFactory做一些处理
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor doing");
    }
}
