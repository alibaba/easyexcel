package com.alibaba.excel.write;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;

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
     *
     */
    private List<Converter> customConverter = new ArrayList<Converter>();

    public ExcelWriter build() {
        new ExcelBuilderImpl(templateInputStream, outputStream, typeEnum, needHead, writeHandler, converters);
    }
}
