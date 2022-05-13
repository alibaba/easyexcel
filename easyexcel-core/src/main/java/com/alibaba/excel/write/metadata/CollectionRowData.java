package com.alibaba.excel.write.metadata;

import java.util.Collection;

/**
 * A collection row of data.
 *
 * @author Jiaju Zhuang
 */
public class CollectionRowData implements RowData {

    private final Object[] array;

    public CollectionRowData(Collection<?> collection) {
        this.array = collection.toArray();
    }

    @Override
    public Object get(int index) {
        return array[index];
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }
}
