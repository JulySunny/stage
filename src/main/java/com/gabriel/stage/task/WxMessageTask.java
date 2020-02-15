package com.gabriel.stage.task;

import com.gabriel.stage.common.enums.WxAppEnum;
import com.gabriel.stage.config.IWxAppBaseConfig;
import com.gabriel.stage.config.WxAppConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: yq
 * @date: 2019/12/13 17:35
 * @discription
 */
@Slf4j
@Component
public class WxMessageTask {

    public void pushMessage(){
        IWxAppBaseConfig config = WxAppConfigFactory.getConfig(WxAppEnum.CORP_DAILY);
        System.out.println(config);
        System.out.println(config.getCorpid());
        System.out.println();
        System.out.println("=======================");
        IWxAppBaseConfig config1 = WxAppConfigFactory.getConfig(WxAppEnum.WG_DATA);
        System.out.println(config1.getCorpid());
        System.out.println(config1);
    }


}
