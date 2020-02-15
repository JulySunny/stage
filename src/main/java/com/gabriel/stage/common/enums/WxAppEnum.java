package com.gabriel.stage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Gabriel
 * @date: 2020/2/14 18:11
 * @description
 */
@Getter
@AllArgsConstructor
public enum WxAppEnum {

    CORP_DAILY(1,"经营日报"),

    WG_DATA(2,"微供有数");

    /** 企业微信代码 */
    private int code;
    /** 企业微信应用 */
    private String wxAppName;
}
