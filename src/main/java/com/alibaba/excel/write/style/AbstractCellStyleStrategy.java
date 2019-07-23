package com.alibaba.excel.write.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.holder.SheetHolder;
import com.alibaba.excel.write.metadata.holder.TableHolder;
import com.alibaba.excel.write.metadata.holder.WorkbookHolder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;

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
    public void afterWorkbookCreate(WorkbookHolder workbookHolder) {
        initCellStyle(workbookHolder.getWorkbook());
    }

    @Override
    public void beforeCellCreate(SheetHolder sheetHolder, TableHolder tableHolder, Row row, Head head,
                                 int relativeRowIndex, boolean isHead) {}

    @Override
    public void afterCellCreate(SheetHolder sheetHolder, TableHolder tableHolder, Cell cell, Head head,
                                int relativeRowIndex, boolean isHead) {
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
