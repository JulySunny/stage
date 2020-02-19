package com.gabriel.stage.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.stage.annotation.RateLimitAnno;
import com.gabriel.stage.common.enums.ResultCode;
import com.gabriel.stage.exception.BusinessException;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Gabriel
 * @date: 2020/2/18 12:07
 * @description
 */
@Slf4j
@Aspect
@Component
public class RateLimitAspect {

    /**
     * 用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
     */
    private ConcurrentHashMap<String, RateLimiter> map = new ConcurrentHashMap<>();

    private RateLimiter rateLimiter;

    @Autowired
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Pointcut("@annotation(com.gabriel.stage.annotation.RateLimitAnno)")
    public void rateLimit() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    @Around("rateLimit()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object obj = null;
        //获取拦截的方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object target = joinPoint.getTarget();
        //获取注解信息
        Method method = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
        RateLimitAnno annotation = method.getAnnotation(RateLimitAnno.class);
        double limitNum = annotation.limitNum();
        //获取方法名
        String functionName = signature.getName();
        //获取类名
        String className = signature.getDeclaringTypeName();
        signature.getDeclaringTypeName();
        if (StringUtils.isNotBlank(className)) {
            className = StringUtils.substringAfterLast(className, ".");
        }
        //拼接类名和方法名,保证key唯一
        String joinName = StringUtils.join(functionName, className);

        //获取rateLimiter
        if (map.containsKey(joinName)) {
            rateLimiter = map.get(joinName);
        } else {
            map.put(joinName, RateLimiter.create(limitNum));
            rateLimiter = map.get(joinName);
        }

        if (rateLimiter.tryAcquire()) {
                obj = joinPoint.proceed();
        } else {
            System.err.println("接口限流，请求降级。。。。。。。。。。。。。。。。。");
            throw new BusinessException(ResultCode.SYS_BUSY);
        }
        return obj;
    }

    //将结果返回
    public void outErrorResult(String result) {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (ServletOutputStream outputStream = httpServletResponse.getOutputStream()) {
            outputStream.write(result.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
