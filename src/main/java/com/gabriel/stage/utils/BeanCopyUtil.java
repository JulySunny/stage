package com.gabriel.stage.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class BeanCopyUtil {
    private BeanCopyUtil() { }
    //单个对象复制
    public static <F, T> T copy(F from, Class<T> destCls) {
        if (from == null) {
            return null;
        }

        if (from.getClass().equals(destCls)) {
            return (T) from;
        }
        return JSON.parseObject(JSON.toJSONString(from), destCls);
    }
    private static ValueFilter filter = (obj, s, v) -> {
        if (v == null)
            return "";
        return v;
    };

    //集合复制
    public static <F, T> List<T> copyList(List<F> from, Class<T> destCls) {
        if (CollectionUtils.isEmpty(from)) {
            return Collections.<T>emptyList();
        }
        if (from.get(0).getClass().equals(destCls)) {
            return (List<T>) from;
        }
        return from.stream().map(f -> copy(f, destCls)).collect(Collectors.toList());
    }
    //Page复制
    public static <F, T> IPage<T> copyPage(IPage<F> from, Class<T> destCls) {
        IPage<T> to = new Page<>();
        if (null != from){
            to.setCurrent(from.getCurrent());
            to.setPages(from.getPages());
            to.setSize(from.getSize());
            to.setTotal(from.getTotal());
            List<T> dls = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(from.getRecords())) {
                dls = from.getRecords().stream().map(f -> copy(f, destCls)).collect(Collectors.toList());
            }
            to.setRecords(dls);
        }
        return to;
    }
    //Page复制到集合
    public static <F, T> List<T> copyPageToList(IPage<F> from, Class<T> destCls) {
        List<T> dls = Lists.newArrayList();
        if (null != from){
            if (!CollectionUtils.isEmpty(from.getRecords())) {
                dls = from.getRecords().stream().map(f -> copy(f, destCls)).collect(Collectors.toList());
            }
        }
        return dls;
    }
}
