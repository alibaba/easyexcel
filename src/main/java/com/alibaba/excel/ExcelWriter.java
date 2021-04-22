package com.alibaba.excel;

import java.util.Collection;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.fill.FillConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel Writer This tool is used to write value out to Excel via POI. This object can perform the following two
 * functions.
 *
 * <pre>
 *    1. Create a new empty Excel workbook, write the value to the stream after the value is filled.
 *    2. Edit existing Excel, write the original Excel file, or write it to other places.}
 * </pre>
 *
 * @author jipengfei
 */
public class ExcelWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriter.class);

    private ExcelBuilder excelBuilder;

    /**
     * Create new writer
     *
     * @param writeWorkbook
     */
    public ExcelWriter(WriteWorkbook writeWorkbook) {
        excelBuilder = new ExcelBuilderImpl(writeWorkbook);
    }

    /**
     * Write data to a sheet
     *
     * @param data
     *            Data to be written
     * @param writeSheet
     *            Write to this sheet
     * @return this current writer
     */
    public ExcelWriter write(Collection<?> data, WriteSheet writeSheet) {
        return write(data, writeSheet, null);
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param writeSheet
     *            Write to this sheet
     * @param writeTable
     *            Write to this table
     * @return this
     */
    public ExcelWriter write(Collection<?> data, WriteSheet writeSheet, WriteTable writeTable) {
        excelBuilder.addContent(data, writeSheet, writeTable);
        return this;
    }

    /**
     * Fill value to a sheet
     *
     * @param data
     * @param writeSheet
     * @return
     */
    public ExcelWriter fill(Object data, WriteSheet writeSheet) {
        return fill(data, null, writeSheet);
    }

    /**
     * Fill value to a sheet
     *
     * @param data
     * @param fillConfig
     * @param writeSheet
     * @return
     */
    public ExcelWriter fill(Object data, FillConfig fillConfig, WriteSheet writeSheet) {
        excelBuilder.fill(data, fillConfig, writeSheet);
        return this;
    }

    /**
     * Close IO
     */
    public void finish() {
        if (excelBuilder != null) {
            excelBuilder.finish(false);
        }
    }

    /**
     * Prevents calls to {@link #finish} from freeing the cache
     *
     */
    @Override
    protected void finalize() {
        try {
            finish();
        } catch (Throwable e) {
            LOGGER.warn("Destroy object failed", e);
        }
    }

    /**
     * The context of the entire writing process
     *
     * @return
     */
    public WriteContext writeContext() {
        return excelBuilder.writeContext();
    }
}
