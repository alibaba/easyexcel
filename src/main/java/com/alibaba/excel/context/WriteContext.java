package com.alibaba.excel.context;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.holder.ConfigurationSelector;

/**
 * Write context
 * 
 * @author jipengfei
 */
public interface WriteContext {
    /**
     * If the current sheet already exists, select it; if not, create it
     * 
     * @param sheet
     */
    void currentSheet(com.alibaba.excel.metadata.Sheet sheet);

    /**
     * If the current table already exists, select it; if not, create it
     * 
     * @param table
     */
    void currentTable(Table table);

    /**
     * Configuration of currently operated cell
     * 
     * @return
     */
    ConfigurationSelector configurationSelector();

    Sheet getCurrentSheet();

    ExcelHeadProperty getExcelHeadProperty();

    Workbook getWorkbook();

    /**
     * close
     */
    void finish();
}
