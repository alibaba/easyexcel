package com.alibaba.excel.write.builder;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteWorkbook;

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

    public ExcelWriterBuilder() {
        this.writeWorkbook = new WriteWorkbook();
    }

    /**
     * Default true
     *
     * @param autoCloseStream
     * @return
     */
    public ExcelWriterBuilder autoCloseStream(Boolean autoCloseStream) {
        writeWorkbook.setAutoCloseStream(autoCloseStream);
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
        writeWorkbook.setPassword(password);
        return this;
    }

    /**
     * Write excel in memory. Default false,the cache file is created and finally written to excel.
     * <p>
     * Comment and RichTextString are only supported in memory mode.
     */
    public ExcelWriterBuilder inMemory(Boolean inMemory) {
        writeWorkbook.setInMemory(inMemory);
        return this;
    }

    /**
     * Excel is also written in the event of an exception being thrown.The default false.
     */
    public ExcelWriterBuilder writeExcelOnException(Boolean writeExcelOnException) {
        writeWorkbook.setWriteExcelOnException(writeExcelOnException);
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
        writeWorkbook.setConvertAllFiled(convertAllFiled);
        return this;
    }

    public ExcelWriterBuilder excelType(ExcelTypeEnum excelType) {
        writeWorkbook.setExcelType(excelType);
        return this;
    }

    public ExcelWriterBuilder file(OutputStream outputStream) {
        writeWorkbook.setOutputStream(outputStream);
        return this;
    }

    public ExcelWriterBuilder file(File outputFile) {
        writeWorkbook.setFile(outputFile);
        return this;
    }

    public ExcelWriterBuilder file(String outputPathName) {
        return file(new File(outputPathName));
    }

    public ExcelWriterBuilder withTemplate(InputStream templateInputStream) {
        writeWorkbook.setTemplateInputStream(templateInputStream);
        return this;
    }

    public ExcelWriterBuilder withTemplate(File templateFile) {
        writeWorkbook.setTemplateFile(templateFile);
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
        writeWorkbook.setWriteHandler(writeHandler);
        return this;
    }

    public ExcelWriter build() {
        return new ExcelWriter(writeWorkbook);
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
}
