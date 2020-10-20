package com.afeiluo.annotation;

import java.lang.annotation.*;

/**
 * Created by ben on 16/10/9.
 * 元注解(注解的注解)包含四个
 * @Document 是否生产javaDoc
 * @Target 定制注解的作用目标
 * @Retention 定义注解的保留策略 SOURCE 源码,CLASS 字节码,RUNTIME 运行时 可以通过反射获取
 * @Inherited 说明子类可以继承父类的该注解
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Yts {
    public enum YtsType {util, entity, service, model}


    public YtsType classType() default YtsType.util;
}
