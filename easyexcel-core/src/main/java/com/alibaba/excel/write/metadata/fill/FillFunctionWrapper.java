package com.alibaba.excel.write.metadata.fill;

import java.util.Map;
import java.util.function.Function;

/**
 * Description:
 * 填充方法包裹类
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/25 14:34
 */
public class FillFunctionWrapper {

    private boolean prefix;

    /**
     * The collection prefix that needs to be filled.
     */
    private String name;
    /**
     * Function of filling in data
     */
    private Map<String, Function<String, ?>> columnMap;
    /**
     * Fill quantity
     */
    private Integer fillSize;

    public FillFunctionWrapper(Map<String, Function<String, ?>> columnMap, Integer size) {
        this.columnMap = columnMap;
        this.fillSize = size;
    }

    public FillFunctionWrapper(boolean hasPrefix, String name, Map<String, Function<String, ?>> columnMap, Integer fillSize) {
        this.prefix = hasPrefix;
        this.name = name;
        this.columnMap = columnMap;
        this.fillSize = fillSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Function<String, ?>> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Function<String, ?>> columnMap) {
        this.columnMap = columnMap;
    }

    public Integer getFillSize() {
        return fillSize;
    }

    public void setFillSize(Integer fillSize) {
        this.fillSize = fillSize;
    }

    public boolean hasPrefix() {
        return prefix;
    }
}
