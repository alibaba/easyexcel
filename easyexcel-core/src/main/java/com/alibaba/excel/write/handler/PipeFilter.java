package com.alibaba.excel.write.handler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Description:
 * pipe filter
 *
 * @author linfeng
 */
public interface PipeFilter<T, R> extends Function<PipeDataWrapper<T>, PipeDataWrapper<R>> {

    /**
     * apply
     *
     * @param wrapper the function argument
     * @return data wrapper
     */
    @Override
    PipeDataWrapper<R> apply(PipeDataWrapper<T> wrapper);

    /**
     * filter params collection
     *
     * @return params collection
     */
    List<String> params();

    /**
     * add param to pipe filter
     *
     * @param params param
     * @return pipe filter
     */
    default PipeFilter<T, R> addParams(String... params) {
        params().addAll(Arrays.asList(params));
        return this;
    }
}
