package com.alibaba.excel.write.style.column;

import java.util.List;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Column width style strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractColumnWidthStyleStrategy implements CellWriteHandler,NotRepeatExecutor {

    @Override
    public String uniqueValue() {
        return "ColumnWidthStyleStrategy";
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        setColumnWidth(writeSheetHolder, cellDataList, cell, head, relativeRowIndex, isHead);
    }

    /**
     * Sets the column width when head create
     *
     * @param writeSheetHolder
     * @param cellDataList
     * @param cell
     * @param head
     * @param relativeRowIndex
     * @param isHead
     */
    protected abstract void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData<?>> cellDataList, Cell cell,
        Head head, Integer relativeRowIndex, Boolean isHead);

}
