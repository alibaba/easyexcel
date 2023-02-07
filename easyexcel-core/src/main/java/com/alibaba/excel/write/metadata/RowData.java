package com.alibaba.excel.write.metadata;

/**
 * A row of data.
 *
 * @author Jiaju Zhuang
 */
public interface RowData {

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param index
     * @return data
     */
    Object get(int index);

    /**
     * Returns the number of elements in this collection.  If this collection
     * contains more than <code>Integer.MAX_VALUE</code> elements, returns
     * <code>Integer.MAX_VALUE</code>.
     *
     * @return the number of elements in this collection
     */
    int size();

    /**
     * Returns <code>true</code> if this collection contains no elements.
     *
     * @return <code>true</code> if this collection contains no elements
     */
    boolean isEmpty();

}
