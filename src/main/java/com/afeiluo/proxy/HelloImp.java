package com.afeiluo.proxy;

/**
 * @author ben
 * @date 2019/8/31
 */

// 实现
public class HelloImp implements Hello {
    @Override
    public String sayHello(String str) {
        return "HelloImp: " + str;
    }
}
