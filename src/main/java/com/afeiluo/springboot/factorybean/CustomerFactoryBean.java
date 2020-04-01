package com.afeiluo.springboot.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by qiaolinfei on 2020/3/27.
 */
@Component
public class CustomerFactoryBean implements FactoryBean<UserService> {

    @Override
    public UserService getObject() throws Exception {
        return new UserService();
    }

    @Override
    public Class<?> getObjectType() {
        return UserService.class;
    }
}
