package com.alibaba.excel.read.builder;

import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;


public abstract class XLSX extends Sheets {
    @Override
    void createWorkBook(WriteWorkbookHolder writeWorkbookHolder) throws IOException {
        if (writeWorkbookHolder.getTempTemplateInputStream() != null) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(writeWorkbookHolder.getTempTemplateInputStream());
            writeWorkbookHolder.setCachedWorkbook(xssfWorkbook);
            if (writeWorkbookHolder.getInMemory()) {
                writeWorkbookHolder.setWorkbook(xssfWorkbook);
            } else {
                writeWorkbookHolder.setWorkbook(new SXSSFWorkbook(xssfWorkbook));
            }
            return;
        }
        Workbook workbook;
        if (writeWorkbookHolder.getInMemory()) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
        writeWorkbookHolder.setCachedWorkbook(workbook);
        writeWorkbookHolder.setWorkbook(workbook);
        return;
    }

}
