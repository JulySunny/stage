package com.gabriel.stage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author: Gabriel
 * @date: 2020/1/17 14:40
 * @description 响应码枚举类
 */
@Getter
@AllArgsConstructor
public enum  ResultCode {
    /**
     * Success response code.
     *
     * @desc 请求操作成功
     */
    SUCCESS(0, "操作成功!"),
    /**
     * Error response code.
     *
     * @desc 请求操作失败
     */
    ERROR(1, "操作失败!"),
    /**
     * Illegal argument response code.
     *
     * @desc 参数错误
     */
    ILLEGAL_ARGUMENT(2, "请求参数错误!"),
    /**
     * Need login response code.
     *
     * @desc 需要登录
     */
    NEED_LOGIN(50000, "登录超时,请重新登录!"),

    /**
     * Need login response code.
     *
     * @desc 需要登录
     */
    NO_LOGIN(401, "未登录,请重新登录!"),
    /**
     * Exception response code.
     *
     * @desc 服务器内部错误
     */
    EXCEPTION(500, "该请求发生异常,请稍后重试!"),
    /**
     * Sys not found error response code.
     */
    SYS_NOT_FOUND_ERROR(404, "未找到相应资源!"),

    DATABASE_ERROR(6000,"数据库异常,请稍后重试!"),
    /**
     * Sys not use error response code.
     */
    SYS_NOT_USE_ERROR(403, "资源不可用!");

    private final int code;

    private final String desc;

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static ResultCode getEnumByCode(int code){
        return Arrays.stream(values())
                .filter(x->Objects.equals(x,code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("没有找到对应的枚举类型"));
    }

    /**
     * 根据code获取枚举值
     * @param code
     * @return
     */
    public static String getDescByCode(int code){
        return Arrays.stream(values())
                .filter(x->Objects.equals(x,code))
                .map(ResultCode::getDesc)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("没有找到对应的枚举值"));
    }
}
