package com.gabriel.stage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gabriel.stage.entity.Test;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author: Gabriel
 * @date: 2020/1/18 0:05
 * @description
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
