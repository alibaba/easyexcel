package com.alibaba.excel.write.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * Cell style strategy
 *
 * @author zhuangjiaju
 */
public abstract class AbstractCellStyleStrategy implements CellWriteHandler, SheetWriteHandler, NotRepeatExecutor {
    boolean hasInitialized = false;

    @Override
    public String uniqueValue() {
        return "CellStyleStrategy";
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        initCellStyle(writeWorkbookHolder.getWorkbook());
        hasInitialized = true;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Head head, int relativeRowIndex, boolean isHead) {
        if (!hasInitialized) {
            initCellStyle(writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook());
            hasInitialized = true;
        }
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
