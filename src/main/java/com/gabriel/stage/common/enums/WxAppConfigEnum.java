package com.gabriel.stage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: Gabriel
 * @date: 2020/2/14 18:38
 * @description
 */
@Getter
@AllArgsConstructor
public enum  WxAppConfigEnum {

    WG_DATA_CONFIG(WxAppEnum.WG_DATA,"wgDataWechatConfig"),
    CORP_DAILY_CONFIG(WxAppEnum.CORP_DAILY,"corpWechatConfig");

    private WxAppEnum wxAppEnum;
    private String configName;

    /**
     * 获取微信应用配置枚举
     * @param valueName
     * @return
     */
    public static WxAppConfigEnum valueOfConfigName(String valueName){
        return Stream.of(values()).filter(x->x.getConfigName().equals(valueName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取微信应用枚举
     * @param valueName
     * @return
     */
    public static WxAppEnum getWxAppEnum(String valueName){
        Optional<WxAppConfigEnum> optional = Optional.ofNullable(valueOfConfigName(valueName));
        return optional.isPresent()?optional.get().getWxAppEnum():null;
    }
}
