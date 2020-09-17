package com.gabriel.stage.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Sam.yang
 * @date: 2020/9/15 11:41
 * @desc: Jackson 解析器
 */
@NoArgsConstructor
public class JacksonUtil {

    /**
     * 单个对象复制
     */
    @SneakyThrows
    public static <F, T> T copy(F from, Class<T> destCls) {
        if (from == null) {
            return null;
        }

        if (from.getClass().equals(destCls)) {
            return (T) from;
        }
        ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(mapper.writeValueAsString(from), destCls);
    }


    /**
     * 集合复制
     */
    public static <F, T> List<T> copyList(List<F> from, Class<T> destCls) {
        if (CollectionUtils.isEmpty(from)) {
            return Collections.<T>emptyList();
        }
        if (from.get(0).getClass().equals(destCls)) {
            return (List<T>) from;
        }
        return from.stream().map(f -> copy(f, destCls)).collect(Collectors.toList());
    }

    /**
     * Page复制
     */
    public static <F, T> IPage<T> copyPage(IPage<F> from, Class<T> destCls) {
        IPage<T> to = new Page<>();
        if (null != from) {
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

    /**
     * Page复制到集合
     */
    public static <F, T> List<T> copyPageToList(IPage<F> from, Class<T> destCls) {
        List<T> dls = Lists.newArrayList();
        if (null != from) {
            if (!CollectionUtils.isEmpty(from.getRecords())) {
                dls = from.getRecords().stream().map(f -> copy(f, destCls)).collect(Collectors.toList());
            }
        }
        return dls;
    }
}
