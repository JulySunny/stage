package com.gabriel.stage.exception;

import com.gabriel.stage.common.enums.ResultCode;
import lombok.Data;

/**
 * @author: Gabriel
 * @date: 2020/1/17 14:56
 * @description 自定义异常
 */
@Data
public class BusinessException extends RuntimeException {

    /** 状态码 */
    protected int code;

    /** 错误信息 */
    protected String message;

    /** 枚举值 */
    private ResultCode resultCode;


    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.message = resultCode.getDesc();
    }
}
