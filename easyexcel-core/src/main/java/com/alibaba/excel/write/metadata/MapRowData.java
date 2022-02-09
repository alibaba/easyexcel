package com.alibaba.excel.write.metadata;

import java.util.Map;

/**
 * A map row of data.
 *
 * @author Jiaju Zhuang
 */
public class MapRowData implements RowData {

    private final Map<Integer, ?> map;

    public MapRowData(Map<Integer, ?> map) {
        this.map = map;
    }

    @Override
    public Object get(int index) {
        return map.get(index);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

}
