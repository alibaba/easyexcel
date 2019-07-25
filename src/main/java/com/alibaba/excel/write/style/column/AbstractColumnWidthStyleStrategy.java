package com.alibaba.excel.write.style.column;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.sun.istack.internal.Nullable;

/**
 * Column width style strategy
 * 
 * @author zhuangjiaju
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
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, int relativeRowIndex, boolean isHead) {
        if (!isHead && relativeRowIndex != 0) {
            return;
        }
        setColumnWidth(writeSheetHolder.getSheet(), cell, head);
    }

    /**
     * Sets the column width when head create
     * 
     * @param sheet
     * @param cell
     * @param head
     */
    protected abstract void setColumnWidth(Sheet sheet, Cell cell, @Nullable Head head);

}
