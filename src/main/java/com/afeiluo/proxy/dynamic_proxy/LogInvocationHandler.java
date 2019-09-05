package com.afeiluo.proxy.dynamic_proxy;


import com.afeiluo.proxy.Hello;
import com.afeiluo.proxy.HelloImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author ben
 * @date 2019/8/31
 */
public class LogInvocationHandler implements InvocationHandler {

    private Hello hello;

    private Logger logger = LoggerFactory.getLogger("proxy");

    public LogInvocationHandler(Hello hello) {
        this.hello = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("sayHello".equals(method.getName())) {
            logger.info("You said: " + Arrays.toString(args));
        }
        return method.invoke(hello, args);
    }

    public static void main(String[] args) {
        Hello hello = (Hello) Proxy.newProxyInstance(
                LogInvocationHandler.class.getClassLoader(), // 1. 类加载器
                new Class<?>[]{Hello.class}, // 2. 代理需要实现的接口，可以有多个
                new LogInvocationHandler(new HelloImp()));// 3. 方法调用的实际处理者
        System.out.println(hello.sayHello("I love you!"));
    }
}
