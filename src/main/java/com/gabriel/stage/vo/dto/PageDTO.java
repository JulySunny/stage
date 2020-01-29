package com.gabriel.stage.vo.dto;

import lombok.Data;

import java.util.Optional;

/**
 * @author: Gabriel
 * @date: 2020/1/18 12:47
 * @description 分页对象
 */
@Data
public class PageDTO {

    /** 页数 */
    private Integer currentPage;

    /** 条数 */
    private Integer pageSize;

    public Integer getCurrentPage() {
        return Optional.ofNullable(currentPage).orElse(1);
    }

    public Integer getPageSize() {
        return Optional.ofNullable(pageSize).orElse(10);
    }
}
