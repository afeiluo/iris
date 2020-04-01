package com.afeiluo.springboot;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by qiaolinfei on 2020/3/26.
 */
public class User implements InitializingBean {
    private String name;
    private Integer age;
    private boolean initialized = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (initialized) {
            System.out.println("User 已经初始化了");
            return;
        }
        System.out.println("开始初始化 User");
        name = "micheal";
        age = 23;
        initialized = true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
