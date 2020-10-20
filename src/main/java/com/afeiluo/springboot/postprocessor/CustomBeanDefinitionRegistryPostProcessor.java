package com.afeiluo.springboot.postprocessor;

import com.afeiluo.spring.beans.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by qiaolinfei on 2020/7/12.
 * 把第三方bean添加到ioc容器里面去
 */
@Component
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * 这个方法被调用的时候, 所有的BeanDefinition已经被加载了, 但是所有的Bean还没被创建
     * 定义 --> 实例化 --> 初始化
     * 在Bean被定义但还没被实例化的时候执行。
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry~~~~");
        // 创建一个Bean然后添加到BeanDefinitionRegistry里面去
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(User.class).addConstructorArgValue("tuacy").addConstructorArgValue(18);
        //设置属性值
        builder.addPropertyValue("age", 28);
        //设置可通过@Autowire注解引用
        builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
        //注册到BeanDefinitionRegistry
        registry.registerBeanDefinition("user", builder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}

