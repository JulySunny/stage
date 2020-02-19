package com.gabriel.stage.config.wxconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Gabriel
 * @date: 2019/12/17 9:10
 * @discription
 */
@Data
@Component
@ConfigurationProperties(prefix = "corpwechat")
public class CorpWechatConfig extends IWxAppBaseConfig {

}
