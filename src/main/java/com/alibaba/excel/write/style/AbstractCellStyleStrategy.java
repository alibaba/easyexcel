package com.alibaba.excel.write.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.event.NotRepeatExecutor;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.TableHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;

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

    protected abstract void initCellStyle(Workbook workbook);

    protected abstract void setHeadCellStyle(Cell cell, Head head, int relativeRowIndex);

    protected abstract void setContentCellStyle(Cell cell, Head head, int relativeRowIndex);

}
