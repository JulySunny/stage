package com.gabriel.stage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gabriel.stage.entity.Car;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Gabriel
 * @date: 2020/1/28 19:26
 * @description
 */
@Mapper
public interface CarMapper extends BaseMapper<Car> {
}
