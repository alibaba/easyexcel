package com.alibaba.excel.write.style.column;

import com.alibaba.excel.metadata.Head;

/**
 * All the columns are the same width
 *
 * @author Jiaju Zhuang
 */
public class SimpleColumnWidthStyleStrategy extends AbstractHeadColumnWidthStyleStrategy {
    private Integer columnWidth;

    /**
     *
     * @param columnWidth
     */
    public SimpleColumnWidthStyleStrategy(Integer columnWidth) {
        this.columnWidth = columnWidth;
    }

    @Override
    protected Integer columnWidth(Head head, Integer columnIndex) {
        return columnWidth;
    }
}
