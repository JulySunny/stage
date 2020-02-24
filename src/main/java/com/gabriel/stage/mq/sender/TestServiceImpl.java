package com.gabriel.stage.mq.sender;

import com.gabriel.stage.common.Result;
import com.gabriel.stage.config.RabbitConfig;
import com.gabriel.stage.entity.Mail;
import com.gabriel.stage.entity.MsgLog;
import com.gabriel.stage.mapper.MsgLogMapper;
import com.gabriel.stage.mq.MessageHelper;
import com.gabriel.stage.service.TestService;
import com.gabriel.stage.utils.RandomUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Gabriel
 * @date: 2020/2/19 23:44
 * @description
 */
@Service(value = "testMq")
public class TestServiceImpl implements TestService {
    @Autowired
    private MsgLogMapper msgLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result send(Mail mail) {
        String msgId = RandomUtil.UUID32();
        mail.setMsgId(msgId);

        MsgLog msgLog = new MsgLog(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        // 消息入库
        msgLogMapper.insert(msgLog);

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);// 发送消息

        return Result.success();
    }

}
