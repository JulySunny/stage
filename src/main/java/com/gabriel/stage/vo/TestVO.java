package com.gabriel.stage.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gabriel.stage.config.Decimal2Serializer;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Gabriel
 * @date: 2020/3/7 15:45
 * @description
 */
@Data
public class TestVO {

    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal bigDecimal;
}
