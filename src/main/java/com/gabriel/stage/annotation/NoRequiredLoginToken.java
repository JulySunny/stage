package com.gabriel.stage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Gabriel
 * @date: 2020/2/5 12:43
 * @description 无需登录token
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRequiredLoginToken {

    /** 必须 默认是true */
    boolean required() default true;
}
