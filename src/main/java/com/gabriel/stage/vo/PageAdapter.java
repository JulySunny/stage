package com.gabriel.stage.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author: Gabriel
 * @date: 2020/1/18 14:08
 * @description 分页适配器 适配器+代理模式
 */
public class PageAdapter<T> extends PageVO<T> {
    private PageVO<T> proxy;

    /**
     * PageHelper分页适配 构造方法
     */
    public PageAdapter(Page<T> page) {
        proxy = new PageVO<>();
        proxy.setCurrentPage(page.getPageNum());
        proxy.setPageSize(page.getPageSize());
        proxy.setTotalPage(page.getPages());
        proxy.setTotalCount((int) page.getTotal());
        proxy.setList(page.getResult());
    }

    /**
     * Mybatis plus 分页适配 构造方法
     */
    public PageAdapter(IPage<T> page) {
        proxy = new PageVO<>();
        proxy.setCurrentPage((int) page.getCurrent());
        proxy.setPageSize((int) page.getSize());
        proxy.setTotalPage((int) page.getPages());
        proxy.setTotalCount((int) page.getTotal());
        proxy.setList(page.getRecords());
    }

    @Override
    public Integer getCurrentPage() {
        return proxy.getCurrentPage();
    }

    @Override
    public Integer getPageSize() {
        return proxy.getPageSize();
    }

    @Override
    public Integer getTotalPage() {
        return proxy.getTotalPage();
    }

    @Override
    public Integer getTotalCount() {
        return proxy.getTotalCount();
    }

    @Override
    public List<T> getList() {
        return proxy.getList();
    }
}
