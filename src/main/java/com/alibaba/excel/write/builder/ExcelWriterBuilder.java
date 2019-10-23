package com.alibaba.excel.write.builder;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteWorkbook;

/**
 * Build ExcelBuilder
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriterBuilder {
    /**
     * Workbook
     */
    private WriteWorkbook writeWorkbook;

    public ExcelWriterBuilder() {
        this.writeWorkbook = new WriteWorkbook();
    }

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param relativeHeadRowIndex
     * @return
     */
    public ExcelWriterBuilder relativeHeadRowIndex(Integer relativeHeadRowIndex) {
        writeWorkbook.setRelativeHeadRowIndex(relativeHeadRowIndex);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterBuilder#head(List)} and {@link ExcelWriterBuilder#head(Class)}
     *
     * @param head
     * @return
     */
    public ExcelWriterBuilder head(List<List<String>> head) {
        writeWorkbook.setHead(head);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterBuilder#head(List)} and {@link ExcelWriterBuilder#head(Class)}
     *
     * @param clazz
     * @return
     */
    public ExcelWriterBuilder head(Class clazz) {
        writeWorkbook.setClazz(clazz);
        return this;
    }

    /**
     * Need Head
     */
    public ExcelWriterBuilder needHead(Boolean needHead) {
        writeWorkbook.setNeedHead(needHead);
        return this;
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
     * Use the default style.Default is true.
     *
     * @param useDefaultStyle
     * @return
     */
    public ExcelWriterBuilder useDefaultStyle(Boolean useDefaultStyle) {
        writeWorkbook.setUseDefaultStyle(useDefaultStyle);
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
     * Ignore the custom columns.
     */
    public ExcelWriterBuilder excludeColumnIndexes(Collection<Integer> excludeColumnIndexes) {
        writeWorkbook.setExcludeColumnIndexes(excludeColumnIndexes);
        return this;
    }

    /**
     * Ignore the custom columns.
     */
    public ExcelWriterBuilder excludeColumnFiledNames(Collection<String> excludeColumnFiledNames) {
        writeWorkbook.setExcludeColumnFiledNames(excludeColumnFiledNames);
        return this;
    }

    /**
     * Only output the custom columns.
     */
    public ExcelWriterBuilder includeColumnIndexes(Collection<Integer> includeColumnIndexes) {
        writeWorkbook.setIncludeColumnIndexes(includeColumnIndexes);
        return this;
    }

    /**
     * Only output the custom columns.
     */
    public ExcelWriterBuilder includeColumnFiledNames(Collection<String> includeColumnFiledNames) {
        writeWorkbook.setIncludeColumnFiledNames(includeColumnFiledNames);
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

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelWriterBuilder registerConverter(Converter converter) {
        if (writeWorkbook.getCustomConverterList() == null) {
            writeWorkbook.setCustomConverterList(new ArrayList<Converter>());
        }
        writeWorkbook.getCustomConverterList().add(converter);
        return this;
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public ExcelWriterBuilder registerWriteHandler(WriteHandler writeHandler) {
        if (writeWorkbook.getCustomWriteHandlerList() == null) {
            writeWorkbook.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        writeWorkbook.getCustomWriteHandlerList().add(writeHandler);
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

}
