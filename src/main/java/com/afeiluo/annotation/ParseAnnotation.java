package com.afeiluo.annotation;

import java.lang.reflect.Method;

/**
 * Created by ben on 16/10/9.
 */
public class ParseAnnotation {
    public void parseMethod(Class clazz) throws Exception {
        Object obj = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
        for (Method method : clazz.getDeclaredMethods()) {
            HelloWorld say = method.getAnnotation(HelloWorld.class);//获取方法上的指定的注解
            String name = "";
            if (say != null) {
                name = say.name();
                method.invoke(obj, name);
            }
            Yts yts = (Yts) method.getAnnotation(Yts.class);
            if (yts != null) {
                if (Yts.YtsType.util.equals(yts.classType())) {
                    System.out.println("this is a util method");
                } else {
                    System.out.println("this is a other method");
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public void parseType(Class clazz) throws Exception {
        Yts yts = (Yts) clazz.getAnnotation(Yts.class);
        if (yts != null) {
            System.out.println("this is a " + yts.classType() + " class");
        }
    }

    public static void main(String[] args) throws Exception {
        ParseAnnotation parse = new ParseAnnotation();
        System.out.println("解析方法~~~~~~~~~~");
        parse.parseMethod(SayHell.class);
        System.out.println("解析类型~~~~~~~~~~");
        parse.parseType(SayHell.class);
    }
}
