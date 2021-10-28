package com.alibaba.excel.write.style.column;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;

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

    @Override
    public void beforeCellCreate(CellWriteHandlerContext context) {

    }

    @Override
    public void afterCellCreate(CellWriteHandlerContext context) {

    }

    @Override
    public void afterCellDataConverted(CellWriteHandlerContext context) {

    }
}
