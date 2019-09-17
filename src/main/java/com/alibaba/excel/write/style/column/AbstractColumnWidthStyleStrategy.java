package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

/**
 * Column width style strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractColumnWidthStyleStrategy implements CellWriteHandler, NotRepeatExecutor {

    @Override
    public String uniqueValue() {
        return "ColumnWidthStyleStrategy";
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Head head, int relativeRowIndex, boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData,
        Cell cell, Head head, int relativeRowIndex, boolean isHead) {
        setColumnWidth(writeSheetHolder, cellData, cell, head, relativeRowIndex, isHead);
    }

    /**
     * Sets the column width when head create
     *
     * @param writeSheetHolder
     * @param cellData
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    protected abstract void setColumnWidth(WriteSheetHolder writeSheetHolder, CellData cellData, Cell cell, Head head,
        int relativeRowIndex, boolean isHead);

}
