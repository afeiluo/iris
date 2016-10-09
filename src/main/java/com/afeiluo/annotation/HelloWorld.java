package com.afeiluo.annotation;

import java.lang.annotation.*;

/**
 * Created by ben on 16/10/9.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface HelloWorld {
    public String name() default "";
}
