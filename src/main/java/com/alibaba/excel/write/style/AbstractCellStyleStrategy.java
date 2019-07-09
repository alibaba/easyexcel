package com.alibaba.excel.write.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.SheetHolder;
import com.alibaba.excel.metadata.TableHolder;
import com.alibaba.excel.write.handler.CellExcelWriteHandler;
import com.alibaba.excel.write.handler.WookbookExcelWriteHandler;

public abstract class AbstractCellStyleStrategy implements CellExcelWriteHandler, WookbookExcelWriteHandler {
    @Override
    public void beforeWookbookCreate() {}

    @Override
    public void afterWookbookCreate(Workbook workbook) {
        initCellStyle(workbook);
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
