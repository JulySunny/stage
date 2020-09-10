package com.gabriel.stage.service.impl;

import com.gabriel.stage.common.Constant;
import com.gabriel.stage.common.Result;
import com.gabriel.stage.common.enums.ResultCode;
import com.gabriel.stage.exception.BusinessException;
import com.gabriel.stage.service.TokenService;
import com.gabriel.stage.utils.RandomUtil;
import com.gabriel.stage.utils.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
/**
 * @author: Gabriel
 * @date: 2020/1/17 14:56
 * @description
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private RedisService redisService;

    @Override
    public Result createToken() {
        String str = RandomUtil.UUID32();
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        redisService.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE.longValue());

        return Result.success(token.toString());
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new BusinessException(ResultCode.ILLEGAL_ARGUMENT);
            }
        }

        if (!redisService.exists(token)) {
            throw new BusinessException(ResultCode.SERVER_ERROR);
        }

        Boolean flag = redisService.remove(token);
        if (!flag) {
            throw new BusinessException(ResultCode.SERVER_ERROR);
        }
    }

}
