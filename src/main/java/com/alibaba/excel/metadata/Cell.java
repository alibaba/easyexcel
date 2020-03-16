package com.alibaba.excel.metadata;

/**
 * Cell
 *
 * @author Jiaju Zhuang
 **/
public interface Cell {
    /**
     * Row index
     *
     * @return
     */
    Integer getRowIndex();

    /**
     * Column index
     *
     * @return
     */
    Integer getColumnIndex();
}
