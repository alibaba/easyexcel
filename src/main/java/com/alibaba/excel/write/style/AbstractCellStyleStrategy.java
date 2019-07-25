package com.alibaba.excel.write.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * Cell style strategy
 * 
 * @author zhuangjiaju
 */
public abstract class AbstractCellStyleStrategy implements CellWriteHandler, WorkbookWriteHandler, NotRepeatExecutor {
    @Override
    public String uniqueValue() {
        return "CellStyleStrategy";
    }

    @Override
    public void beforeWorkbookCreate() {}

    @Override
    public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {
        initCellStyle(writeWorkbookHolder.getWorkbook());
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Head head, int relativeRowIndex, boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, int relativeRowIndex, boolean isHead) {
        if (isHead) {
            setHeadCellStyle(cell, head, relativeRowIndex);
        } else {
            setContentCellStyle(cell, head, relativeRowIndex);
        }
    }

    /**
     * Initialization cell style
     * 
     * @param workbook
     */
    protected abstract void initCellStyle(Workbook workbook);

    /**
     * Sets the cell style of header
     * 
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected abstract void setHeadCellStyle(Cell cell, Head head, int relativeRowIndex);

    /**
     * Sets the cell style of content
     * 
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected abstract void setContentCellStyle(Cell cell, Head head, int relativeRowIndex);

}
