package com.gabriel.stage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author: Gabriel
 * @date: 2020/1/18 0:06
 * @description 测试实体类
 */
@TableName("test")
public class Test {

    /** 主键Id-自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 名称-测试 */
    private String name;
}
