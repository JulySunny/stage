package com.gabriel.stage.config;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.gabriel.stage.common.Result;
import com.gabriel.stage.common.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author: Gabriel
 * @date: 2020/1/28 22:08
 * @description 服务日志切面
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    /** 注解切点 */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
              "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
              "@annotation(org.springframework.web.bind.annotation.PutMapping)||" +
              "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void requestMapping(){
    }

    /** 控制器方法切点 */
    @Pointcut("execution(* com.gabriel.stage.controller.*Controller.*(..))")
    public void methodPointCut(){
    }

    @Around("requestMapping() && methodPointCut()")
    public Object logAround(ProceedingJoinPoint pjd)throws Throwable{
        Signature signature = pjd.getSignature();
        //请求类名
        String className = signature.getClass().getName();
        //请求方法名
        String methodName = signature.getName();
        //记录日志入库
        Object[] requestParamArray = pjd.getArgs();
        StopWatch watch =new StopWatch();
        watch.start();
        int index =0;
        StringBuffer sb = new StringBuffer();
        for (Object requestParam : requestParamArray) {
            if (ObjectUtil.isNull(requestParam)) {
                index++;
                continue;
            }
            try {
                sb.append(JSON.toJSONString(requestParam));

                //
            }catch (Exception e){
                sb.append(requestParam.toString());
            }
            sb.append(",");
        }
        String concatParam = sb.toString();

        if (concatParam.length()>0) {
            concatParam=concatParam.substring(0,concatParam.length()-1);
        }

        //记录请求
        log.info(String.format("【%s】类的【%s】方法,请求参数：%s",className,methodName,concatParam));

        //执行服务方法-环绕通知能决定是否执行目标方法,且返回值与目标方法的返回值一致
        Object response = pjd.proceed();


        watch.stop();

        //记录应答
        log.info(String.format("【%s】类的【%s】方法,返回参数：%s",className,methodName,JSON.toJSONString(response)));

        // 获取执行完的时间
        log.info(String.format("接口【%s】总耗时(毫秒)：%s", methodName, watch.getTotalTimeMillis()));


        System.out.println("Spring AOP方式记录标准请求-应答模型服务日志");
        //请求参数
        Object request=null;

        if (ArrayUtil.isNotEmpty(requestParamArray)) {
             request = requestParamArray[index];
        }

        Result result = (Result) response;
        //记录日志
        //TODO 可以记录入库
        String msg = String.format("请求：%s======响应：%s======总耗时(毫秒)：%s", JSON.toJSONString(request),
                JSON.toJSONString(response), watch.getTotalTimeMillis());

        if (result.getStatus()== ResultCode.SUCCESS.getCode()) {
            log.info(msg);
        } else {
            log.error(msg);//记录错误日志
        }

        return response;
    }

}
