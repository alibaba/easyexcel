package com.alibaba.excel.write.builder;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;

/**
 * Build sheet
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriterSheetBuilder {
    private ExcelWriter excelWriter;
    /**
     * Sheet
     */
    private WriteSheet writeSheet;

    public ExcelWriterSheetBuilder() {
        this.writeSheet = new WriteSheet();
    }

    public ExcelWriterSheetBuilder(ExcelWriter excelWriter) {
        this.writeSheet = new WriteSheet();
        this.excelWriter = excelWriter;
    }

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param relativeHeadRowIndex
     * @return
     */
    public ExcelWriterSheetBuilder relativeHeadRowIndex(Integer relativeHeadRowIndex) {
        writeSheet.setRelativeHeadRowIndex(relativeHeadRowIndex);
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
        writeSheet.setHead(head);
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
        writeSheet.setClazz(clazz);
        return this;
    }

    /**
     * Need Head
     */
    public ExcelWriterSheetBuilder needHead(Boolean needHead) {
        writeSheet.setNeedHead(needHead);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelWriterSheetBuilder registerConverter(Converter converter) {
        if (writeSheet.getCustomConverterList() == null) {
            writeSheet.setCustomConverterList(new ArrayList<Converter>());
        }
        writeSheet.getCustomConverterList().add(converter);
        return this;
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public ExcelWriterSheetBuilder registerWriteHandler(WriteHandler writeHandler) {
        if (writeSheet.getCustomWriteHandlerList() == null) {
            writeSheet.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        writeSheet.getCustomWriteHandlerList().add(writeHandler);
        return this;
    }

    /**
     * Starting from 0
     *
     * @param sheetNo
     * @return
     */
    public ExcelWriterSheetBuilder sheetNo(Integer sheetNo) {
        writeSheet.setSheetNo(sheetNo);
        return this;
    }

    /**
     * sheet name
     *
     * @param sheetName
     * @return
     */
    public ExcelWriterSheetBuilder sheetName(String sheetName) {
        writeSheet.setSheetName(sheetName);
        return this;
    }

    public WriteSheet build() {
        return writeSheet;
    }

    public void doWrite(List data) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'EasyExcelFactory.write().sheet()' to call this method");
        }
        excelWriter.write(data, build());
        excelWriter.finish();
    }

    public void doFill(Object data) {
        doFill(data, null);
    }

    public void doFill(Object data, FillConfig fillConfig) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'EasyExcelFactory.write().sheet()' to call this method");
        }
        excelWriter.fill(data, fillConfig, build());
        excelWriter.finish();
    }

    /**
     * write with password
     * @param data
     * @param password
     */
    public void doWrite(List data,String password) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'EasyExcelFactory.write().sheet()' to call this method");
        }
        excelWriter.write(data, build());
        excelWriter.doEncrypt(password);
        excelWriter.finish();
    }

    public ExcelWriterTableBuilder table() {
        return table(null);
    }

    public ExcelWriterTableBuilder table(Integer tableNo) {
        ExcelWriterTableBuilder excelWriterTableBuilder = new ExcelWriterTableBuilder(excelWriter, build());
        if (tableNo != null) {
            excelWriterTableBuilder.tableNo(tableNo);
        }
        return excelWriterTableBuilder;
    }

}
