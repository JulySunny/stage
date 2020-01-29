package com.gabriel.stage.vo;

import com.gabriel.stage.vo.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: Gabriel
 * @date: 2020/1/18 12:48
 * @description 分页响应对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageVO<T>  extends PageDTO {
    /**
     * 总页数
     */
    private Integer totalPage = 0;

    /**
     * 总条数
     */
    private Integer totalCount = 0;

    /**
     * 列表数据
     */
    private List<T> list = Collections.emptyList();

    /**
     * 修改 list 中的对象类型
     */
    public <R> PageVO<R> map(Function<T, R> mapper) {
        PageVO<R> pageVO = new PageVO<>();
        pageVO.setCurrentPage(getCurrentPage());
        pageVO.setPageSize(getPageSize());
        pageVO.setTotalPage(getTotalPage());
        pageVO.setTotalCount(getTotalCount());
        pageVO.setList(getList().stream().map(mapper).collect(Collectors.toList()));
        return pageVO;
    }

    /**
     * 遍历 list 中的数据
     */
    public void forEach(Consumer<T> action) {
        getList().forEach(action);
    }
}
