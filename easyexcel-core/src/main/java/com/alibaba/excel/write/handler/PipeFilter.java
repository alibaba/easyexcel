package com.alibaba.excel.write.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Description:
 * pipeline filter
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/27 21:58
 */
public abstract class PipeFilter<T, R> implements Function<T, R> {

    private final List<String> filterParams = new ArrayList<>();

    /**
     * filter 参数
     *
     * @return 参数集合
     */
    protected List<String> params() {
        return filterParams;
    }

    /**
     * 添加参数
     *
     * @param params 参数
     * @return 过滤器
     */
    public PipeFilter<T, R> addParams(String... params) {
        params().addAll(Arrays.asList(params));
        return this;
    }
}
