package com.alibaba.excel.context;

import java.io.OutputStream;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Table;

public interface WriteContext {

    Sheet getCurrentSheet();

    boolean needHead();

    ExcelHeadProperty getExcelHeadProperty();

    void currentSheet(com.alibaba.excel.metadata.Sheet sheet);
    
    void currentTable(Table table);

    OutputStream getOutputStream();

    Workbook getWorkbook();
    
    WriteHandler getWriteHandler();

    CellStyle getCurrentContentStyle();
    
    ConverterRegistryCenter getConverterRegistryCenter();
}
