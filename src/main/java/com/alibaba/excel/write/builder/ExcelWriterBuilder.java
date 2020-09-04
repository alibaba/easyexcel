package com.alibaba.excel.write.builder;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteWorkbook;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Build ExcelBuilder
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriterBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterBuilder, WriteWorkbook> {
    /**
     * Workbook
     */
    private WriteWorkbook writeWorkbook;

    /**
     * excelBuilder
     */
    private ExcelBuilder excelBuilder;

    public ExcelWriterBuilder() {
        this.writeWorkbook = new WriteWorkbook();
    }

    public ExcelWriterBuilder(WriteWorkbook writeWorkbook) {
        this.writeWorkbook = writeWorkbook;
    }

    public ExcelWriterBuilder(WriteWorkbook writeWorkbook, ExcelBuilder excelBuilder) {
        this.writeWorkbook = writeWorkbook;
        this.excelBuilder = excelBuilder;
    }

    public ExcelWriterBuilder(ExcelBuilder excelBuilder) {
        this();
        this.excelBuilder = excelBuilder;
    }

    /**
     * Default true
     *
     * @param autoCloseStream
     * @return
     */
    public ExcelWriterBuilder autoCloseStream(Boolean autoCloseStream) {
        this.parameter().setAutoCloseStream(autoCloseStream);
        return this;
    }

    /**
     * Whether the encryption.
     * <p>
     * WARRING:Encryption is when the entire file is read into memory, so it is very memory intensive.
     *
     * @param password
     * @return
     */
    public ExcelWriterBuilder password(String password) {
        this.parameter().setPassword(password);
        return this;
    }

    /**
     * Write excel in memory. Default false,the cache file is created and finally written to excel.
     * <p>
     * Comment and RichTextString are only supported in memory mode.
     */
    public ExcelWriterBuilder inMemory(Boolean inMemory) {
        this.parameter().setInMemory(inMemory);
        return this;
    }

    /**
     * Excel is also written in the event of an exception being thrown.The default false.
     */
    public ExcelWriterBuilder writeExcelOnException(Boolean writeExcelOnException) {
        this.parameter().setWriteExcelOnException(writeExcelOnException);
        return this;
    }

    /**
     * The default is all excel objects.if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a
     * field. if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
     * <p>
     * Default true
     *
     * @param convertAllFiled
     * @return
     * @deprecated Just to be compatible with historical data, The default is always going to be convert all filed.
     */
    @Deprecated
    public ExcelWriterBuilder convertAllFiled(Boolean convertAllFiled) {
        this.parameter().setConvertAllFiled(convertAllFiled);
        return this;
    }

    public ExcelWriterBuilder excelType(ExcelTypeEnum excelType) {
        this.parameter().setExcelType(excelType);
        return this;
    }

    public ExcelWriterBuilder file(OutputStream outputStream) {
        this.parameter().setOutputStream(outputStream);
        return this;
    }

    public ExcelWriterBuilder file(File outputFile) {
        this.parameter().setFile(outputFile);
        return this;
    }

    public ExcelWriterBuilder file(String outputPathName) {
        return file(new File(outputPathName));
    }

    public ExcelWriterBuilder withTemplate(InputStream templateInputStream) {
        this.parameter().setTemplateInputStream(templateInputStream);
        return this;
    }

    public ExcelWriterBuilder withTemplate(File templateFile) {
        this.parameter().setTemplateFile(templateFile);
        return this;
    }

    public ExcelWriterBuilder withTemplate(String pathName) {
        return withTemplate(new File(pathName));
    }

    /**
     * Write handler
     *
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    public ExcelWriterBuilder registerWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {
        this.parameter().setWriteHandler(writeHandler);
        return this;
    }

    public ExcelWriter build() {
        return excelBuilder != null
            ? new ExcelWriter(this.parameter(), excelBuilder)
            : new ExcelWriter(this.parameter());
    }

    public ExcelWriterSheetBuilder sheet() {
        return sheet(null, null);
    }

    public ExcelWriterSheetBuilder sheet(Integer sheetNo) {
        return sheet(sheetNo, null);
    }

    public ExcelWriterSheetBuilder sheet(String sheetName) {
        return sheet(null, sheetName);
    }

    public ExcelWriterSheetBuilder sheet(Integer sheetNo, String sheetName) {
        ExcelWriter excelWriter = build();
        ExcelWriterSheetBuilder excelWriterSheetBuilder = new ExcelWriterSheetBuilder(excelWriter);
        if (sheetNo != null) {
            excelWriterSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            excelWriterSheetBuilder.sheetName(sheetName);
        }
        return excelWriterSheetBuilder;
    }

    @Override
    protected WriteWorkbook parameter() {
        return writeWorkbook;
    }

    public WriteWorkbook getWriteWorkbook() {
        return this.parameter();
    }

    public ExcelWriterBuilder setWriteWorkbook(WriteWorkbook writeWorkbook) {
        this.writeWorkbook = writeWorkbook;
        return this;
    }

    public ExcelWriterBuilder setExcelBuilder(ExcelBuilder excelBuilder) {
        this.excelBuilder = excelBuilder;
        return this;
    }
}
