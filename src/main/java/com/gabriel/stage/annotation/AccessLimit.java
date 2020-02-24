package com.gabriel.stage.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Gabriel
 * @date: 2020/2/5 12:43
 * @description 接口防刷限流注解(Redis实现) 在需要保证 接口防刷限流 的Controller的方法上使用此注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {


    /**
     * 最大访问次数
     */
    int maxCount();

    /**
     * 固定时间, 单位: s
     */
    int seconds();

}
