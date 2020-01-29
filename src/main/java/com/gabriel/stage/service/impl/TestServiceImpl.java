package com.gabriel.stage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gabriel.stage.entity.Test;
import com.gabriel.stage.mapper.TestMapper;
import com.gabriel.stage.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * @author: Gabriel
 * @date: 2020/1/18 0:04
 * @description 测试service
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
}
