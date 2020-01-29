package com.gabriel.stage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: Gabriel
 * @date: 2020/1/28 19:21
 * @description 测试类SQL优化实体类-- Car
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "car")
@Builder
public class Car {

    /** 指定自增id */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 名称 */
    @TableField(value = "name")
    private String name;

    /**  品牌 */
    @TableField(value = "brand")
    private String brand;

    /** 价格 */
    @TableField(value = "price")
    private BigDecimal price;
}
