package com.afeiluo.proxy.static_proxy;

import com.afeiluo.proxy.Hello;
import com.afeiluo.proxy.HelloImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ben
 * @date 2019/8/31
 */
public class StaticProxiedHello implements Hello {
    private Hello hello = new HelloImp();

    private Logger logger = LoggerFactory.getLogger("proxy");

    @Override
    public String sayHello(String str) {
        logger.info("You said: " + str);
        return hello.sayHello(str);
    }
}
