package com.alibaba.excel.context;

import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.excel.enums.WriteTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.holder.WriteHolder;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * Write context
 *
 * @author jipengfei
 */
public interface WriteContext {
    /**
     * If the current sheet already exists, select it; if not, create it
     *
     * @param writeSheet
     *            Current sheet
     * @param writeType
     */
    void currentSheet(WriteSheet writeSheet, WriteTypeEnum writeType);

    /**
     * If the current table already exists, select it; if not, create it
     *
     * @param writeTable
     */
    void currentTable(WriteTable writeTable);

    /**
     * All information about the workbook you are currently working on
     *
     * @return
     */
    WriteWorkbookHolder writeWorkbookHolder();

    /**
     * All information about the sheet you are currently working on
     *
     * @return
     */
    WriteSheetHolder writeSheetHolder();

    /**
     * All information about the table you are currently working on
     *
     * @return
     */
    WriteTableHolder writeTableHolder();

    /**
     * Configuration of currently operated cell. May be 'writeSheetHolder' or 'writeTableHolder' or
     * 'writeWorkbookHolder'
     *
     * @return
     */
    WriteHolder currentWriteHolder();

    /**
     * close
     *
     * @param onException
     */
    void finish(boolean onException);

    /**
     * Current sheet
     *
     * @return
     * @deprecated please us e{@link #writeSheetHolder()}
     */
    @Deprecated
    Sheet getCurrentSheet();

    /**
     * Need head
     *
     * @return
     * @deprecated please us e{@link #writeSheetHolder()}
     */
    @Deprecated
    boolean needHead();

    /**
     * Get outputStream
     *
     * @return
     * @deprecated please us e{@link #writeWorkbookHolder()} ()}
     */
    @Deprecated
    OutputStream getOutputStream();

    /**
     * Get workbook
     *
     * @return
     * @deprecated please us e{@link #writeWorkbookHolder()} ()}
     */
    @Deprecated
    Workbook getWorkbook();

}
