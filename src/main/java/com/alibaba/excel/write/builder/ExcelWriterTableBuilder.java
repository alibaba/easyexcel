package com.alibaba.excel.write.builder;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;

/**
 * Build sheet
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriterTableBuilder {

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
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param relativeHeadRowIndex
     * @return
     */
    public ExcelWriterTableBuilder relativeHeadRowIndex(Integer relativeHeadRowIndex) {
        writeTable.setRelativeHeadRowIndex(relativeHeadRowIndex);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterTableBuilder#head(List)} and
     * {@link ExcelWriterTableBuilder#head(Class)}
     *
     * @param head
     * @return
     */
    public ExcelWriterTableBuilder head(List<List<String>> head) {
        writeTable.setHead(head);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterTableBuilder#head(List)} and
     * {@link ExcelWriterTableBuilder#head(Class)}
     *
     * @param clazz
     * @return
     */
    public ExcelWriterTableBuilder head(Class clazz) {
        writeTable.setClazz(clazz);
        return this;
    }

    /**
     * Need Head
     */
    public ExcelWriterTableBuilder needHead(Boolean needHead) {
        writeTable.setNeedHead(needHead);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelWriterTableBuilder registerConverter(Converter converter) {
        if (writeTable.getCustomConverterList() == null) {
            writeTable.setCustomConverterList(new ArrayList<Converter>());
        }
        writeTable.getCustomConverterList().add(converter);
        return this;
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public ExcelWriterTableBuilder registerWriteHandler(WriteHandler writeHandler) {
        if (writeTable.getCustomWriteHandlerList() == null) {
            writeTable.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        writeTable.getCustomWriteHandlerList().add(writeHandler);
        return this;
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

}
