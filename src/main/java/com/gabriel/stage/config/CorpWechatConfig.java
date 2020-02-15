package com.gabriel.stage.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: yq
 * @date: 2019/12/17 9:10
 * @discription
 */
@Data
@Component
@ToString(callSuper = false)
@ConfigurationProperties(prefix = "corpwechat")
public class CorpWechatConfig extends IWxAppBaseConfig{

}
