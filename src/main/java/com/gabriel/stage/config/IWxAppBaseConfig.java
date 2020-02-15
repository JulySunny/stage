package com.gabriel.stage.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author: yq
 * @date: 2020/2/14 17:52
 * @description
 */
@Data
@ToString
public  class IWxAppBaseConfig {

    /** 企业Id */
    private String corpid;

    /** 企业号密钥 */
    private String corpsecret;

    /** 企业应用的id */
    private Integer agentId;

    /** 消息推送url */
    private String sendMessageUrl;

}
