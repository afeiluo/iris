package com.afeiluo.springboot.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 使用BeanFactoryPostProcessor实现一个简单的功能,
 * 找到helloFactoryPostProcessorService对应的bean修改desc属性对应的值
 */
@Component
public class CustomizeBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) beanFactory.getBeanDefinition("beanService");
        MutablePropertyValues pv = abstractBeanDefinition.getPropertyValues();
        pv.addPropertyValue("desc", "hello service");
        abstractBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
    }
}