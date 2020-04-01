package com.afeiluo.springboot.listener;

import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * 获取已经自动加载的配置类的信息
 * Created by qiaolinfei on 2020/3/26.
 */
public class MyAutoConfigurationImportListener implements AutoConfigurationImportListener {
    @Override
    public void onAutoConfigurationImportEvent(AutoConfigurationImportEvent event) {
        // Acquire current ClassLoader
        ClassLoader classLoader = event.getClass().getClassLoader();
        // Candidate autoconfig list
        List<String> candidates = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, classLoader);
        System.out.println("================output print candidates configurations");
        candidates.forEach(System.out::println);
        System.out.println("================output print registered configurations");
        event.getCandidateConfigurations().forEach(System.out::println);
        System.out.println("================output print exclusions configurations");
        event.getExclusions().forEach(System.out::println);
    }
}
