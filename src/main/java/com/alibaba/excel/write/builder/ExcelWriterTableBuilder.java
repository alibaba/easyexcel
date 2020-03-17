package com.alibaba.excel.write.builder;

import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;

/**
 * Build sheet
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriterTableBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterTableBuilder, WriteTable> {

    private ExcelWriter excelWriter;

    private WriteSheet writeSheet;
    /**
     * table
     */
    private WriteTable writeTable;

    public ExcelWriterTableBuilder() {
        this.writeTable = new WriteTable();
    }

    public ExcelWriterTableBuilder(ExcelWriter excelWriter, WriteSheet writeSheet) {
        this.excelWriter = excelWriter;
        this.writeSheet = writeSheet;
        this.writeTable = new WriteTable();
    }

    /**
     * Starting from 0
     *
     * @param tableNo
     * @return
     */
    public ExcelWriterTableBuilder tableNo(Integer tableNo) {
        writeTable.setTableNo(tableNo);
        return this;
    }

    public WriteTable build() {
        return writeTable;
    }

    public void doWrite(List data) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'EasyExcelFactory.write().sheet().table()' to call this method");
        }
        excelWriter.write(data, writeSheet, build());
        excelWriter.finish();
    }

    @Override
    protected WriteTable parameter() {
        return writeTable;
    }
}
