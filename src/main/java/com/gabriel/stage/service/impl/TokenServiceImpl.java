package com.gabriel.stage.service.impl;

import com.gabriel.stage.common.Constant;
import com.gabriel.stage.common.Result;
import com.gabriel.stage.common.enums.ResultCode;
import com.gabriel.stage.exception.BusinessException;
import com.gabriel.stage.utils.JedisUtil;
import com.gabriel.stage.utils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import com.gabriel.stage.service.TokenService;
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
    private JedisUtil jedisUtil;

    @Override
    public Result createToken() {
        String str = RandomUtil.UUID32();
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE);

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

        if (!jedisUtil.exists(token)) {
            throw new BusinessException(ResultCode.SYS_BUSY);
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new BusinessException(ResultCode.SYS_BUSY);
        }
    }

}
