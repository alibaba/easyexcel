package com.alibaba.excel.write.style.row;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.excel.metadata.SheetHolder;
import com.alibaba.excel.metadata.TableHolder;
import com.alibaba.excel.write.handler.RowExcelWriteHandler;

public abstract class AbstractRowHighStyleStrategy implements RowExcelWriteHandler {

    @Override
    public void beforeRowCreate(SheetHolder sheetHolder, TableHolder tableHolder, int rowIndex, int relativeRowIndex,
        boolean isHead) {

    }

    @Override
    public void afterRowCreate(SheetHolder sheetHolder, TableHolder tableHolder, Row row, int relativeRowIndex,
        boolean isHead) {
        if (isHead) {
            setHeadColumnHigh(row, relativeRowIndex);
        } else {
            setContentColumnHigh(row, relativeRowIndex);
        }
    }

    protected abstract void setHeadColumnHigh(Row row, int relativeRowIndex);

    protected abstract void setContentColumnHigh(Row row, int relativeRowIndex);

}
