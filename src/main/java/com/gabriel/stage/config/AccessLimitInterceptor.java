package com.gabriel.stage.config;

import com.gabriel.stage.annotation.AccessLimit;
import com.gabriel.stage.common.Constant;
import com.gabriel.stage.common.enums.ResultCode;
import com.gabriel.stage.exception.BusinessException;
import com.gabriel.stage.utils.IpUtil;
import com.gabriel.stage.utils.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * @author: Gabriel
 * @date: 2020/2/5 12:43
 * @description 接口防刷限流拦截器
 */
@Slf4j
@Component

public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        if (annotation != null) {
            check(annotation, request);
        }

        return true;
    }

    private void check(AccessLimit annotation, HttpServletRequest request) {
        int maxCount = annotation.maxCount();
        int seconds = annotation.seconds();

        StringBuilder sb = new StringBuilder();
        sb.append(Constant.Redis.ACCESS_LIMIT_PREFIX).append(IpUtil.getIpAddress(request)).append(request.getRequestURI());
        String key = sb.toString();

        Boolean exists = jedisUtil.exists(key);
        if (!exists) {
            jedisUtil.set(key, String.valueOf(1), seconds);
        } else {
            int count = Integer.valueOf(jedisUtil.get(key));
            if (count < maxCount) {
                Long ttl = jedisUtil.ttl(key);
                if (ttl <= 0) {
                    jedisUtil.set(key, String.valueOf(1), seconds);
                } else {
                    jedisUtil.set(key, String.valueOf(++count), ttl.intValue());
                }
            } else {
                throw new BusinessException(ResultCode.SERVER_ERROR);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
