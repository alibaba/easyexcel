package com.alibaba.excel.write.handler;

import java.util.*;

/**
 * Description:
 * pipeline filter
 *
 * @author linfeng
 */
public abstract class BasePipeFilter<T, R> implements PipeFilter<T, R> {

    private final List<String> filterParams = new ArrayList<>();
    protected int rowIndex;
    protected int columnIndex;

    /**
     * filter name
     *
     * @return filter name
     */
    protected abstract String filterName();

    protected boolean isValidity(PipeDataWrapper<R> apply) {
        Object data = apply.getData();
        if (Objects.nonNull(data)) {
            return !(data instanceof Collection || data instanceof Map);
        }
        return true;
    }

    /**
     * @return Error message prefix
     */
    protected String errorPrefix() {
        return String.format("Column [%s], [%s] instruction error:", columnIndex + 1, filterName());
    }

    /**
     * filter params
     *
     * @return params collection
     */
    public List<String> params() {
        return filterParams;
    }

    /**
     * add param
     *
     * @param params collection
     * @return filter
     */
    public BasePipeFilter<T, R> addParams(String... params) {
        params().addAll(Arrays.asList(params));
        return this;
    }

    /**
     * set excel cell
     *
     * @param row    row index
     * @param column column index
     * @return 过滤器
     */
    public BasePipeFilter<T, R> setCell(int row, int column) {
        this.rowIndex = row;
        this.columnIndex = column;
        return this;
    }


    /**
     * verify
     *
     * @param wrapper data
     * @return true
     */
    protected boolean verify(PipeDataWrapper<T> wrapper) {
        return Objects.nonNull(wrapper) && wrapper.success();
    }
}
