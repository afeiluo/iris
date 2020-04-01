package com.afeiluo.springboot.factorybean;


import org.springframework.stereotype.Component;

/**
 * Created by qiaolinfei on 2020/3/27.
 */
public class UserService {
    public UserService() {
        System.out.println("userService construct");
    }
}
