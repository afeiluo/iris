package com.afeiluo.spring.beans;

import com.afeiluo.spring.dao.PrototypeDao;
import com.afeiluo.spring.service.TestService;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Created by qiaolinfei on 2020/3/29.
 */
public class InitialBeans {
    public static void main(String[] args) {
        InitialBeans initialBeans = new InitialBeans();
        initialBeans.loadFromXmlBeanDefinitionReader();
        //initialBeans.registerSingletonWithApplicationContext();
    }

    /**
     * 从context里面获取在xml中配置的bean
     */
    private void loadFromClassPathXmlApplicationContext() {
        // create and configure beans
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/services.xml");
        // retrieve configured instance
        TestService service = context.getBean("testService", TestService.class);
        System.out.println(service);
    }

    /**
     * 从context里面获取在xml中配置的bean
     */
    private void loadFromXmlBeanDefinitionReader() {
        GenericApplicationContext context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("classpath:spring/services.xml"); //location 可以是 spring/services.xml 或者 classpath:spring/services.xml
        context.refresh();
        TestService service = context.getBean("testService", TestService.class);
        System.out.println(service);
        service = context.getBean("testAliasService", TestService.class);
        System.out.println(service);

        PrototypeDao prototypeDao = context.getBean("prototypeDao", PrototypeDao.class);
        System.out.println(prototypeDao);
        prototypeDao = context.getBean("prototypeDao", PrototypeDao.class);
        System.out.println(prototypeDao);
    }

    /**
     * 手动往context里面注册一个bean
     */
    private void registerSingletonWithApplicationContext() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.refresh();
        context.getBeanFactory().registerSingleton("selfDefBean", new User("tes", 1));
        User user = context.getBean("selfDefBean", User.class);
        System.out.println(user);
    }
}
