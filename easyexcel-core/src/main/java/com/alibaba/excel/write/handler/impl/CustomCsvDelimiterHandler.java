package com.alibaba.excel.write.handler.impl;

import com.alibaba.excel.metadata.csv.CsvSheet;
import com.alibaba.excel.metadata.csv.CsvWorkbook;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author chlch
 */
@Slf4j
public class CustomCsvDelimiterHandler implements RowWriteHandler {
    private char delimiter;

    public CustomCsvDelimiterHandler(char delimiter) {
        this.delimiter = delimiter;
    }

    public void beforeRowCreate(RowWriteHandlerContext context) {
        Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
        if (workbook instanceof CsvWorkbook) {
            CsvWorkbook csvWorkbook = (CsvWorkbook)workbook;
            CsvSheet csvSheet = csvWorkbook.getCsvSheet();
            csvSheet.setCsvFormat(csvSheet.getCsvFormat().withDelimiter(delimiter));
        }
    }
}
