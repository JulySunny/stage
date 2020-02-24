package com.gabriel.stage.service;

import com.gabriel.stage.common.Result;

import javax.servlet.http.HttpServletRequest;
/**
 * @author: Gabriel
 * @date: 2020/1/17 14:56
 * @description
 */
public interface TokenService {

    Result createToken();

    void checkToken(HttpServletRequest request);

}
