package com.afeiluo.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by qiaolinfei on 2020/3/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
public class ImportSelectorTests {
    @Test
    public void testSelectImport() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ImportConfig.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
    }

}
