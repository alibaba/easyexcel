package com.alibaba.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Excel Writer This tool is used to write value out to Excel via POI. This object can perform the following two
 * functions.
 * 
 * <pre>
 *    1. Create a new empty Excel workbook, write the value to the stream after the value is filled.
 *    2. Edit existing Excel, write the original Excel file, or write it to other places.}
 * </pre>
 * 
 * @author jipengfei
 */
public class ExcelWriter {
    private ExcelBuilder excelBuilder;

    /**
     * Create new writer
     * 
     * @param templateInputStream
     *            Append value after a POI file ,Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param excelType
     *            03 or 07
     * @param needHead
     * @param customConverterMap
     * @param customWriteHandlerList
     */
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum excelType,
        boolean needHead, Map<Class, Converter> customConverterMap, List<WriteHandler> customWriteHandlerList) {
        excelBuilder = new ExcelBuilderImpl(templateInputStream, outputStream, excelType, needHead, customConverterMap,
            customWriteHandlerList);
    }

    /**
     * Create new writer
     *
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @deprecated please use {@link com.alibaba.excel.write.builder.ExcelWriterBuilder} build ExcelWriter
     */
    @Deprecated
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        this(outputStream, typeEnum, true);
    }

    /**
     *
     * Create new writer
     *
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @param needHead
     *            Do you need to write the header to the file?
     * @deprecated please use {@link com.alibaba.excel.write.builder.ExcelWriterBuilder} build ExcelWriter
     */
    @Deprecated
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        this(null, outputStream, typeEnum, needHead, null, null);
    }

    /**
     * Create new writer
     *
     * @param templateInputStream
     *            Append value after a POI file ,Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @deprecated please use {@link com.alibaba.excel.write.builder.ExcelWriterBuilder} build ExcelWriter
     */
    @Deprecated
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum,
        Boolean needHead) {
        this(templateInputStream, outputStream, typeEnum, needHead, null, null);
    }

    /**
     * Create new writer
     *
     * @param templateInputStream
     *            Append value after a POI file ,Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @param writeHandler
     *            User-defined callback
     * @deprecated please use {@link com.alibaba.excel.write.builder.ExcelWriterBuilder} build ExcelWriter
     */
    @Deprecated
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum,
        Boolean needHead, WriteHandler writeHandler) {
        List<WriteHandler> customWriteHandlerList = new ArrayList<WriteHandler>();
        customWriteHandlerList.add(writeHandler);
        excelBuilder =
            new ExcelBuilderImpl(templateInputStream, outputStream, typeEnum, needHead, null, customWriteHandlerList);
    }

    /**
     * Write data to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @return this current writer
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @param table
     *            Write to this table
     * @return this
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     *
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @return this
     */
    public ExcelWriter write1(List<List<Object>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @return this
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @param table
     *            Write to this table
     * @return this
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @param table
     *            Write to this table
     * @return
     */
    public ExcelWriter write1(List<List<Object>> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Close IO
     */
    public void finish() {
        excelBuilder.finish();
    }

}
