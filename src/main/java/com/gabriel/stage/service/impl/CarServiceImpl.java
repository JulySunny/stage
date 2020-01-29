package com.gabriel.stage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gabriel.stage.entity.Car;
import com.gabriel.stage.mapper.CarMapper;
import com.gabriel.stage.service.ICarService;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author: Gabriel
 * @date: 2020/1/28 19:27
 * @description
 */
@Slf4j
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

    /** 品牌列表 */
    protected static final List<String> brandList= ImmutableList.of("别克","奥迪","奔驰","大众","宝马");

    @Override
    public void createData(){
        for (int i = 0; i < 10; i++) {
            for (int j = 1000; j <= 10000; j++) {
                Car car = Car.builder().brand(brandList.get(new Random().nextInt(4)))
                        .name("car".concat(String.valueOf(j)))
                        .build();
                CompletableFuture.runAsync(() -> this.save(car));
            };
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
