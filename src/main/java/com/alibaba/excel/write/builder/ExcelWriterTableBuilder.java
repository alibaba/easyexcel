package com.alibaba.excel.write.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Build sheet
 * 
 * @author zhuangjiaju
 */
public class ExcelWriterTableBuilder {
    /**
     * table
     */
    private Table table;

    public ExcelWriterTableBuilder() {
        this.table = new Table();
    }

    /**
     * Count the number of added heads when read sheet.
     *
     * <li>0 - This Sheet has no head ,since the first row are the data
     * <li>1 - This Sheet has one row head , this is the default
     * <li>2 - This Sheet has two row head ,since the third row is the data
     *
     * @param readHeadRowNumber
     * @return
     */
    public ExcelWriterTableBuilder readHeadRowNumber(Integer readHeadRowNumber) {
        table.setReadHeadRowNumber(readHeadRowNumber);
        return this;
    }

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param writeRelativeHeadRowIndex
     * @return
     */
    public ExcelWriterTableBuilder writeRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        table.setWriteRelativeHeadRowIndex(writeRelativeHeadRowIndex);
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
        table.setHead(head);
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
        table.setClazz(clazz);
        return this;
    }

    /**
     * Need Head
     */
    public ExcelWriterTableBuilder needHead(Boolean needHead) {
        table.setNeedHead(needHead);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelWriterTableBuilder registerConverter(Converter converter) {
        if (table.getCustomConverterMap() == null) {
            table.setCustomConverterMap(new HashMap<Class, Converter>());
        }
        table.getCustomConverterMap().put(converter.supportJavaTypeKey(), converter);
        return this;
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public ExcelWriterTableBuilder registerWriteHandler(WriteHandler writeHandler) {
        if (table.getCustomWriteHandlerList() == null) {
            table.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        table.getCustomWriteHandlerList().add(writeHandler);
        return this;
    }

    /**
     * Starting from 0
     *
     * @param tableNo
     * @return
     */
    public ExcelWriterTableBuilder tableNo(Integer tableNo) {
        table.setTableNo(tableNo);
        return this;
    }

    public Table build() {
        return table;
    }

}
