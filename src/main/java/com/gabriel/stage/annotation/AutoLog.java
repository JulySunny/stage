package com.gabriel.stage.annotation;


import com.gabriel.stage.common.CommonConstant;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * 
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2019年1月14日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {

	/**
	 * 日志内容
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 日志类型
	 * 
	 * @return 1:登录日志; 2:操作日志;
	 */
	int logType() default CommonConstant.LOG_TYPE_2;
	
	/**
	 * 操作日志类型
	 * 
	 * @return （1查询，2添加，3修改，4删除）
	 */
	int operateType() default 0;
}
