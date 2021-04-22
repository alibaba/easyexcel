package com.alibaba.excel.write.style;

import java.util.List;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Cell style strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractCellStyleStrategy implements CellWriteHandler, WorkbookWriteHandler, NotRepeatExecutor {

    boolean hasInitialized = false;

    @Override
    public String uniqueValue() {
        return "CellStyleStrategy";
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        List<CellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if (isHead == null) {
            return;
        }
        if (isHead) {
            setHeadCellStyle(writeSheetHolder, writeTableHolder, cell, head, relativeRowIndex);
        } else {
            setContentCellStyle(writeSheetHolder, writeTableHolder, cell, head, relativeRowIndex);
        }
    }

    @Override
    public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {
        initCellStyle(writeWorkbookHolder.getWorkbook());
        hasInitialized = true;
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
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected void setHeadCellStyle(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        Cell cell, Head head, Integer relativeRowIndex) {
        setHeadCellStyle(cell, head, relativeRowIndex);
    }

    /**
     * Sets the cell style of header
     *
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        throw new UnsupportedOperationException("Custom styles must override the setHeadCellStyle method.");
    }

    /**
     * Sets the cell style of content
     *
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected void setContentCellStyle(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
        Cell cell, Head head, Integer relativeRowIndex) {
        setContentCellStyle(cell, head, relativeRowIndex);
    }

    /**
     * Sets the cell style of content
     *
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        throw new UnsupportedOperationException("Custom styles must override the setContentCellStyle method.");
    }

}
