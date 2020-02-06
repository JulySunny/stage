package com.gabriel.stage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Gabriel
 * @date: 2020/2/5 12:42
 * @description 需要登录token注解
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredLoginToken {

    boolean required() default true;
}
