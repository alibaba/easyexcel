package com.alibaba.excel.context;

import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.holder.ConfigurationSelector;
import com.alibaba.excel.metadata.holder.SheetHolder;
import com.alibaba.excel.metadata.holder.TableHolder;
import com.alibaba.excel.metadata.holder.WorkbookHolder;

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
    ConfigurationSelector currentConfigurationSelector();

    /**
     * All information about the workbook you are currently working on
     * 
     * @return
     */
    WorkbookHolder currentWorkbookHolder();

    /**
     * All information about the sheet you are currently working on
     * 
     * @return
     */
    SheetHolder currentSheetHolder();

    /**
     * All information about the table you are currently working on
     * 
     * @return
     */
    TableHolder currentTableHolder();

    /**
     * close
     */
    void finish();
}
