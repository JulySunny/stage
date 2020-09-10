package com.gabriel.stage.service;

import com.gabriel.stage.common.Result;
import com.gabriel.stage.entity.Mail;

public interface TestService {


    Result send(Mail mail);
}
