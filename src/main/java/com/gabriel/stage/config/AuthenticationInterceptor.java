package com.gabriel.stage.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gabriel.stage.annotation.NoRequiredLoginToken;
import com.gabriel.stage.annotation.RequiredLoginToken;
import com.gabriel.stage.common.enums.ResultCode;
import com.gabriel.stage.entity.User;
import com.gabriel.stage.exception.BusinessException;
import com.gabriel.stage.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @date: 2020/2/5 13:45
 * @description 登录认证拦截
 */
@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {


     @Autowired
     IUserService userService;

    /**
     * 前置处理-方法执行前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射到方法就放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法及其相关注解
        //检查是否有不需要登录的注解，有则跳过认证
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(NoRequiredLoginToken.class)) {
            NoRequiredLoginToken noRequiredLoginToken = method.getAnnotation(NoRequiredLoginToken.class);
            if (noRequiredLoginToken.required()) {
                return true;
            }
        }

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(RequiredLoginToken.class)) {
            RequiredLoginToken requiredLoginToken = method.getAnnotation(RequiredLoginToken.class);
            if (requiredLoginToken.required()) {
                //执行认证
                if (StringUtils.isBlank(token)) {
                    throw new BusinessException(ResultCode.NO_LOGIN);
                }
                //获取token中的userId
                String userId;
                try {
                    userId=JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    throw new BusinessException(ResultCode.NO_LOGIN);
                }
                User user = userService.getById(userId);
                if (ObjectUtil.isNull(user)) {
                    //用户不存在
                    throw new BusinessException(ResultCode.USER_NOT_EXISTS);
                }
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    //token失效 签名校验失败
                    throw new BusinessException(ResultCode.NO_LOGIN);
                }
                return true;
            }
        }

        //未加注解的方法直接放行-默认是不需要校验的
        return true;
    }

    /**
     * 后置处理-方法执行后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 最终处理-控制器执行完成后执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
