package com.alibaba.excel.write.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.write.metadata.Sheet;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Build sheet
 * 
 * @author zhuangjiaju
 */
public class ExcelWriterSheetBuilder {
    /**
     * Sheet
     */
    private Sheet sheet;

    public ExcelWriterSheetBuilder() {
        this.sheet = new Sheet();
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
    public ExcelWriterSheetBuilder readHeadRowNumber(Integer readHeadRowNumber) {
        sheet.setReadHeadRowNumber(readHeadRowNumber);
        return this;
    }

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param writeRelativeHeadRowIndex
     * @return
     */
    public ExcelWriterSheetBuilder writeRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        sheet.setWriteRelativeHeadRowIndex(writeRelativeHeadRowIndex);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterSheetBuilder#head(List)} and
     * {@link ExcelWriterSheetBuilder#head(Class)}
     *
     * @param head
     * @return
     */
    public ExcelWriterSheetBuilder head(List<List<String>> head) {
        sheet.setHead(head);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterSheetBuilder#head(List)} and
     * {@link ExcelWriterSheetBuilder#head(Class)}
     *
     * @param clazz
     * @return
     */
    public ExcelWriterSheetBuilder head(Class clazz) {
        sheet.setClazz(clazz);
        return this;
    }

    /**
     * Need Head
     */
    public ExcelWriterSheetBuilder needHead(Boolean needHead) {
        sheet.setNeedHead(needHead);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelWriterSheetBuilder registerConverter(Converter converter) {
        if (sheet.getCustomConverterMap() == null) {
            sheet.setCustomConverterMap(new HashMap<Class, Converter>());
        }
        sheet.getCustomConverterMap().put(converter.supportJavaTypeKey(), converter);
        return this;
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public ExcelWriterSheetBuilder registerWriteHandler(WriteHandler writeHandler) {
        if (sheet.getCustomWriteHandlerList() == null) {
            sheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        sheet.getCustomWriteHandlerList().add(writeHandler);
        return this;
    }

    /**
     * Starting from 0
     * 
     * @param sheetNo
     * @return
     */
    public ExcelWriterSheetBuilder sheetNo(Integer sheetNo) {
        sheet.setSheetNo(sheetNo);
        return this;
    }

    /**
     * sheet name
     * 
     * @param sheetName
     * @return
     */
    public ExcelWriterSheetBuilder sheetName(String sheetName) {
        sheet.setSheetName(sheetName);
        return this;
    }

    public Sheet build() {
        return sheet;
    }

}
