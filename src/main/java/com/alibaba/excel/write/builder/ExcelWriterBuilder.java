package com.alibaba.excel.write.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.exception.ExcelGenerateException;
import com.alibaba.excel.write.metadata.Workbook;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Build ExcelBuilder
 * 
 * @author zhuangjiaju
 */
public class ExcelWriterBuilder {
    /**
     * Workbook
     */
    private Workbook workbook;

    public ExcelWriterBuilder() {
        this.workbook = new Workbook();
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
    public ExcelWriterBuilder readHeadRowNumber(Integer readHeadRowNumber) {
        workbook.setReadHeadRowNumber(readHeadRowNumber);
        return this;
    }

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     *
     * @param writeRelativeHeadRowIndex
     * @return
     */
    public ExcelWriterBuilder writeRelativeHeadRowIndex(Integer writeRelativeHeadRowIndex) {
        workbook.setWriteRelativeHeadRowIndex(writeRelativeHeadRowIndex);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterBuilder#head(List)} and {@link ExcelWriterBuilder#head(Class)}
     *
     * @param head
     * @return
     */
    public ExcelWriterBuilder head(List<List<String>> head) {
        workbook.setHead(head);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelWriterBuilder#head(List)} and {@link ExcelWriterBuilder#head(Class)}
     *
     * @param clazz
     * @return
     */
    public ExcelWriterBuilder head(Class clazz) {
        workbook.setClazz(clazz);
        return this;
    }

    /**
     * Need Head
     */
    public ExcelWriterBuilder needHead(Boolean needHead) {
        workbook.setNeedHead(needHead);
        return this;
    }

    /**
     * Default true
     *
     * @param autoCloseStream
     * @return
     */
    public ExcelWriterBuilder autoCloseStream(Boolean autoCloseStream) {
        workbook.setAutoCloseStream(autoCloseStream);
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
        workbook.setConvertAllFiled(convertAllFiled);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelWriterBuilder registerConverter(Converter converter) {
        if (workbook.getCustomConverterMap() == null) {
            workbook.setCustomConverterMap(new HashMap<Class, Converter>());
        }
        workbook.getCustomConverterMap().put(converter.supportJavaTypeKey(), converter);
        return this;
    }

    /**
     * Custom write handler
     *
     * @param writeHandler
     * @return
     */
    public ExcelWriterBuilder registerWriteHandler(WriteHandler writeHandler) {
        if (workbook.getCustomWriteHandlerList() == null) {
            workbook.setCustomWriteHandlerList(new ArrayList<WriteHandler>());
        }
        workbook.getCustomWriteHandlerList().add(writeHandler);
        return this;
    }

    public ExcelWriterBuilder excelType(ExcelTypeEnum excelType) {
        workbook.setExcelType(excelType);
        return this;
    }

    public ExcelWriterBuilder outputFile(OutputStream outputStream) {
        workbook.setOutputStream(outputStream);
        return this;
    }

    public ExcelWriterBuilder outputFile(File outputFile) {
        try {
            return outputFile(new FileOutputStream(outputFile));
        } catch (FileNotFoundException e) {
            throw new ExcelGenerateException("Can not create file", e);
        }
    }

    public ExcelWriterBuilder outputFile(String outputPathName) {
        return outputFile(new File(outputPathName));
    }

    public ExcelWriterBuilder outputFile(URI outputUri) {
        return outputFile(new File(outputUri));
    }

    public ExcelWriterBuilder withTemplate(InputStream inputStream) {
        workbook.setInputStream(inputStream);
        return this;
    }

    public ExcelWriterBuilder withTemplate(File file) {
        workbook.setFile(file);
        return this;
    }

    public ExcelWriterBuilder withTemplate(String pathName) {
        return withTemplate(new File(pathName));
    }

    public ExcelWriterBuilder withTemplate(URI uri) {
        return withTemplate(new File(uri));
    }

    /**
     * Write handler
     *
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    public ExcelWriterBuilder registerWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {
        workbook.setWriteHandler(writeHandler);
        return this;
    }

    public ExcelWriter build() {
        return new ExcelWriter(workbook);
    }
}
