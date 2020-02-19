package com.gabriel.stage.config.wxconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: Gabriel
 * @date: 2019/12/26 15:30
 * @discription
 */
@Data
@Component
@ConfigurationProperties(prefix = "wgdatawechat")
public class WgDataWechatConfig extends IWxAppBaseConfig {
}
