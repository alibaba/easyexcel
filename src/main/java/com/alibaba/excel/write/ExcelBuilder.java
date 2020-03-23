package com.alibaba.excel.write;

import java.util.List;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.fill.FillConfig;

/**
 * @author jipengfei
 */
public interface ExcelBuilder {

    /**
     * WorkBook increase value
     *
     * @param data
     *            java basic type or java model extend BaseModel
     * @param writeSheet
     *            Write the sheet
     * @deprecated please use{@link ExcelBuilder#addContent(List, WriteSheet, WriteTable)}
     */
    @Deprecated
    void addContent(List data, WriteSheet writeSheet);

    /**
     * WorkBook increase value
     *
     * @param data
     *            java basic type or java model extend BaseModel
     * @param writeSheet
     *            Write the sheet
     * @param writeTable
     *            Write the table
     */
    void addContent(List data, WriteSheet writeSheet, WriteTable writeTable);

    /**
     * WorkBook fill value
     *
     * @param data
     * @param fillConfig
     * @param writeSheet
     */
    void fill(Object data, FillConfig fillConfig, WriteSheet writeSheet);

    /**
     * Creates new cell range. Indexes are zero-based.
     *
     * @param firstRow
     *            Index of first row
     * @param lastRow
     *            Index of last row (inclusive), must be equal to or larger than {@code firstRow}
     * @param firstCol
     *            Index of first column
     * @param lastCol
     *            Index of last column (inclusive), must be equal to or larger than {@code firstCol}
     * @deprecated please use{@link OnceAbsoluteMergeStrategy}
     */
    @Deprecated
    void merge(int firstRow, int lastRow, int firstCol, int lastCol);

    /**
     * Gets the written data
     *
     * @return
     */
    WriteContext writeContext();

    /**
     * Close io
     *
     * @param onException
     */
    void finish(boolean onException);

}
