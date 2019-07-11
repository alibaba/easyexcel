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
import com.alibaba.excel.metadata.Workbook;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Build ExcelBuilder
 * 
 * @author zhuangjiaju
 */
public class ExcelWriterBuilder {
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
    /**
     * Write handler
     *
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    private com.alibaba.excel.event.WriteHandler writeHandler;

    public ExcelWriterBuilder excelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
        return this;
    }

    public ExcelWriterBuilder outputFile(OutputStream outputStream) {
        this.outputStream = outputStream;
        return this;
    }

    public ExcelWriterBuilder outputFile(File outputFile) throws FileNotFoundException {
        return outputFile(new FileOutputStream(outputFile));
    }

    public ExcelWriterBuilder outputFile(String outputPathName) throws FileNotFoundException {
        return outputFile(new File(outputPathName));
    }

    public ExcelWriterBuilder outputFile(URI outputUri) throws FileNotFoundException {
        return outputFile(new File(outputUri));
    }

    public ExcelWriterBuilder withTemplate(InputStream templateInputStream) {
        this.templateInputStream = templateInputStream;
        return this;
    }

    public ExcelWriterBuilder withTemplate(File templateFile) throws FileNotFoundException {
        return withTemplate(new FileInputStream(templateFile));
    }

    public ExcelWriterBuilder withTemplate(String templatePathName) throws FileNotFoundException {
        return withTemplate(new File(templatePathName));
    }

    public ExcelWriterBuilder withTemplate(URI templateUri) throws FileNotFoundException {
        return withTemplate(new File(templateUri));
    }

    /**
     * Custom type conversions override the default.
     * 
     * @param converter
     * @return
     */
    public ExcelWriterBuilder registerConverter(Converter converter) {
        this.customConverterMap.put(converter.supportJavaTypeKey(), converter);
        return this;
    }

    /**
     * Default required header
     * 
     * @return
     */
    public ExcelWriterBuilder doNotNeedHead() {
        this.needHead = Boolean.FALSE;
        return this;
    }

    public ExcelWriterBuilder registerWriteHandler(WriteHandler writeHandler) {
        this.customWriteHandlerList.add(writeHandler);
        return this;
    }

    /**
     * Write handler
     *
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    public ExcelWriterBuilder registerWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {
        this.writeHandler = writeHandler;
        return this;
    }

    public ExcelWriter build() {
        Workbook workbook = new Workbook();
        workbook.setTemplateInputStream(templateInputStream);
        workbook.setOutputStream(outputStream);
        workbook.setExcelType(excelType);
        workbook.setNeedHead(needHead);
        workbook.setCustomConverterMap(customConverterMap);
        workbook.setCustomWriteHandlerList(customWriteHandlerList);
        workbook.setWriteHandler(writeHandler);
        return new ExcelWriter(workbook);
    }
}
