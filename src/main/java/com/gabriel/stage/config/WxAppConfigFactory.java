package com.gabriel.stage.config;

import cn.hutool.core.util.ObjectUtil;
import com.gabriel.stage.common.enums.WxAppConfigEnum;
import com.gabriel.stage.common.enums.WxAppEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yq
 * @date: 2020/2/14 17:51
 * @description 企业微信应用配置工厂类
 */
@Component
public class WxAppConfigFactory {


    public static final Map<WxAppEnum,IWxAppBaseConfig> configMap =new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 加载配置
     */
    @PostConstruct
    public void init(){
        applicationContext.getBeansOfType(IWxAppBaseConfig.class)
                .entrySet()
                .stream()
                .filter(x-> !ObjectUtil.isNull(WxAppConfigEnum.valueOfConfigName(x.getKey())))
                .forEach(x->configMap.put(WxAppConfigEnum.getWxAppEnum(x.getKey()),x.getValue()));
    }

    /**
     * 获取配置
     * @param configValue
     * @return
     */
    public static IWxAppBaseConfig getConfig(WxAppEnum configValue){
        return configMap.get(configValue);
    }
}
