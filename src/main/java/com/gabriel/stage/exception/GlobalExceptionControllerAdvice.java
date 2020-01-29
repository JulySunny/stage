package com.gabriel.stage.exception;

import com.gabriel.stage.common.Result;
import com.gabriel.stage.common.enums.ResultCode;
import com.gabriel.stage.utils.IpAddressUtil;
import com.gabriel.stage.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Gabriel
 * @date: 2020/1/17 14:55
 * @description 全局异常拦截
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionControllerAdvice {

    /**
     * 统一异常拦截
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request,Exception exception){
        /**
         * 如果是参数校验异常，不进入日志统计
         */
        if (exception instanceof BindException) {
            BindException e = (BindException) exception;
            List<ObjectError> allErrors = e.getAllErrors();
            String defaultMessage = allErrors.get(0).getDefaultMessage();
            return Result.errorCodeMessage(ResultCode.ILLEGAL_ARGUMENT.getCode(),defaultMessage);
        }
        /**
         * 非参数校验异常 记录日志 ，有助于错误排查
         */
        log.error("服务器内部错误: 请求地址--->{},请求ip--->:{},错误详情:{},请求参数:{}",
                request.getRequestURL(), IpAddressUtil.getIpAddr(request), exception.getMessage(), RequestUtil.getParams(request));

        log.error("服务器内部请求异常",exception);

        if (exception instanceof BusinessException) {
            BusinessException e = (BusinessException) exception;
            return Result.response(e.getResultCode());
        }

        if (StringUtils.isBlank(exception.getMessage())) {
            return Result.errorCodeMessage(ResultCode.EXCEPTION.getCode(),"空指针异常");
        }

        return Result.errorCodeMessage(ResultCode.EXCEPTION.getCode(), exception.getMessage());

    }

}
