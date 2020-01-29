package com.gabriel.stage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gabriel.stage.entity.Car;

/**
 * @author: Gabriel
 * @date: 2020/1/28 19:25
 * @description
 */
public interface ICarService extends IService<Car> {

    /**
     * 批量添加SQl数据
     */
    void createData();

}
