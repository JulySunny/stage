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


    /** 系统模块 */
    SUCCESS(0, "操作成功!"),
    ERROR(1, "操作失败!"),
    SERVER_ERROR(500, "系统繁忙！,请稍后再试"),

    /** 通用模块 */
    ILLEGAL_ARGUMENT(10000, "参数不合法"),
    REPETITIVE_OPERATION(10001, "请勿重复操作"),
    ACCESS_LIMIT(10002, "请求太频繁, 请稍后再试"),
    MAIL_SEND_SUCCESS(10003, "邮件发送成功"),
    DATABASE_ERROR(6000,"数据库异常,请稍后重试!"),

    /** 用户模块 */
    NEED_LOGIN(20001, "登录失效"),
    USERNAME_OR_PASSWORD_EMPTY(20002, "用户名或密码不能为空"),
    USERNAME_OR_PASSWORD_WRONG(20003, "用户名或密码错误"),
    USER_NOT_EXISTS(20004, "用户不存在"),
    WRONG_PASSWORD(20005, "密码错误"),
    NO_LOGIN(20006, "未登录,请重新登录!");


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
