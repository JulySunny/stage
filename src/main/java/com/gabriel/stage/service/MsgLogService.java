package com.gabriel.stage.service;

import com.gabriel.stage.entity.MsgLog;

import java.util.Date;
import java.util.List;

/**
 * @author: Gabriel
 * @date: 2020/2/19 23:33
 * @description
 */
public interface MsgLogService {


    void updateStatus(String msgId, Integer status);

    MsgLog selectByMsgId(String msgId);

    List<MsgLog> selectTimeoutMsg();

    void updateTryCount(String msgId, Date tryTime);
}
