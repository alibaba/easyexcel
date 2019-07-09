package com.alibaba.excel.write.merge;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.SheetHolder;
import com.alibaba.excel.metadata.TableHolder;
import com.alibaba.excel.write.handler.CellExcelWriteHandler;

public abstract class AbstractMergeStrategy implements CellExcelWriteHandler {

    @Override
    public void beforeCellCreate(SheetHolder sheetHolder, TableHolder tableHolder, Row row, Head head,
        int relativeRowIndex, boolean isHead) {}

    @Override
    public void afterCellCreate(SheetHolder sheetHolder, TableHolder tableHolder, Cell cell, Head head,
        int relativeRowIndex, boolean isHead) {
        if (isHead) {
            return;
        }
        merge(sheetHolder.getSheet(), cell, head, relativeRowIndex);
    }

    protected abstract void merge(Sheet sheet, Cell cell, Head head, int relativeRowIndex);
}
