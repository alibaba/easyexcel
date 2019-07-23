package com.alibaba.excel.context;

import com.alibaba.excel.write.metadata.Table;
import com.alibaba.excel.write.metadata.holder.WriteConfiguration;
import com.alibaba.excel.write.metadata.holder.SheetHolder;
import com.alibaba.excel.write.metadata.holder.TableHolder;
import com.alibaba.excel.write.metadata.holder.WorkbookHolder;
import com.alibaba.excel.write.metadata.Sheet;

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
    void currentSheet(Sheet sheet);

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
    WriteConfiguration currentConfiguration();

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
