package com.alibaba.excel.write;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.write.merge.MergeStrategy;

import java.util.List;

/**
 * @author jipengfei
 */
public interface ExcelBuilder {

    /**
     * workBook increase value
     *
     * @param data     java basic type or java model extend BaseModel
     * @param startRow Start row number
     */
    void addContent(List data, int startRow);

    /**
     * WorkBook increase value
     *
     * @param data       java basic type or java model extend BaseModel
     * @param sheetParam Write the sheet
     */
    void addContent(List data, Sheet sheetParam);

    /**
     * WorkBook increase value
     *
     * @param data       java basic type or java model extend BaseModel
     * @param sheetParam Write the sheet
     * @param table      Write the table
     */
    void addContent(List data, Sheet sheetParam, Table table);

    /**
     * Creates new cell range. Indexes are zero-based.
     *
     * @param strategies the merge strategy
     */
    void merge(List<MergeStrategy> strategies);

    /**
     * Close io
     */
    void finish();
}
