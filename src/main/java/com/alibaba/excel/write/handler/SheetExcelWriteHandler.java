package com.alibaba.excel.write.handler;

import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.SheetHolder;

/**
 * intercepts handle sheet creation
 * 
 * @author zhuangjiaju
 */
public interface SheetExcelWriteHandler extends ExcelWriteHandler {

    /**
     * called before create the sheet
     * 
     * @param writeContext
     */
    void beforeSheetCreate(Workbook workbook, SheetHolder sheetHolder);

    /**
     * called after the sheet is created
     *
     * @param writeContext
     */
    void afterSheetCreate(Workbook workbook, SheetHolder sheetHolder);
}
