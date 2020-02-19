package com.gabriel.stage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Gabriel
 * @date: 2020/2/18 12:03
 * @description 自定义接口限流注解
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitAnno {

    /** 每秒放入令牌桶中的token */
    double limitNum() default 20;
}
