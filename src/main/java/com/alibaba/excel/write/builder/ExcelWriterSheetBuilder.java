package com.alibaba.excel.write.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Build sheet
 * 
 * @author zhuangjiaju
 */
public class ExcelWriterSheetBuilder {
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * Final output stream
     */
    private OutputStream outputStream;
    /**
     * Template input stream
     */
    private InputStream templateInputStream;
    /**
     * Custom type conversions override the default
     */
    private Map<Class, Converter> customConverterMap = new HashMap<Class, Converter>();
    /**
     * Need Head
     */
    private Boolean needHead;
    /**
     * Custom type handler override the default
     */
    private List<WriteHandler> customWriteHandlerList = new ArrayList<WriteHandler>();

    public ExcelWriterSheetBuilder excelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
        return this;
    }

    public ExcelWriterSheetBuilder outputFile(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public ExcelWriterSheetBuilder outputFile(File outputFile) throws FileNotFoundException {
        return outputFile(new FileOutputStream(outputFile));
    }

    public ExcelWriterSheetBuilder outputFile(String outputPathName) throws FileNotFoundException {
        return outputFile(new File(outputPathName));
    }

    public ExcelWriterSheetBuilder outputFile(URI outputUri) throws FileNotFoundException {
        return outputFile(new File(outputUri));
    }

    public ExcelWriterSheetBuilder withTemplate(InputStream templateInputStream) {
        this.templateInputStream = templateInputStream;
        return this;
    }

    public ExcelWriterSheetBuilder withTemplate(File templateFile) throws FileNotFoundException {
        return withTemplate(new FileInputStream(templateFile));
    }

    public ExcelWriterSheetBuilder withTemplate(String templatePathName) throws FileNotFoundException {
        return withTemplate(new File(templatePathName));
    }

    public ExcelWriterSheetBuilder withTemplate(URI templateUri) throws FileNotFoundException {
        return withTemplate(new File(templateUri));
    }

    /**
     * Custom type conversions override the default.
     * 
     * @param converter
     * @return
     */
    public ExcelWriterSheetBuilder registerConverter(Converter converter) {
        this.customConverterMap.put(converter.supportJavaTypeKey(), converter);
        return this;
    }

    /**
     * Default required header
     * 
     * @return
     */
    public ExcelWriterSheetBuilder doNotNeedHead() {
        this.needHead = Boolean.FALSE;
        return this;
    }

    public ExcelWriterSheetBuilder registerWriteHandler(WriteHandler writeHandler) {
        this.customWriteHandlerList.add(writeHandler);
        return this;
    }

    public ExcelWriter build() {
        return new ExcelWriter(templateInputStream, outputStream, excelType, needHead, customConverterMap,
            customWriteHandlerList);
    }
}
