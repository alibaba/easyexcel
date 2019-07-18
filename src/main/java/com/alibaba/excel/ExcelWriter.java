package com.alibaba.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.Workbook;
import com.alibaba.excel.parameter.GenerateParam;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;

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
        Workbook workbook = new Workbook();
        workbook.setInputStream(templateInputStream);
        workbook.setOutputStream(outputStream);
        workbook.setExcelType(excelType);
        workbook.setNeedHead(needHead);
        workbook.setCustomConverterMap(customConverterMap);
        workbook.setCustomWriteHandlerList(customWriteHandlerList);
        excelBuilder = new ExcelBuilderImpl(workbook);
    }

    /**
     * Create new writer
     * 
     * @param workbook
     */
    public ExcelWriter(Workbook workbook) {
        excelBuilder = new ExcelBuilderImpl(workbook);
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
        Workbook workbook = new Workbook();
        workbook.setInputStream(templateInputStream);
        workbook.setOutputStream(outputStream);
        workbook.setExcelType(typeEnum);
        workbook.setNeedHead(needHead);
        workbook.setCustomWriteHandlerList(customWriteHandlerList);
        excelBuilder = new ExcelBuilderImpl(workbook);
    }

    /**
     * @param generateParam
     * @deprecated please use {@link com.alibaba.excel.write.builder.ExcelWriterBuilder} build ExcelWriter
     */
    @Deprecated
    public ExcelWriter(GenerateParam generateParam) {
        this(generateParam.getOutputStream(), generateParam.getType(), true);
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
    public ExcelWriter write(List data, Sheet sheet) {
        return write(data, sheet, null);
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
    public ExcelWriter write(List data, Sheet sheet, Table table) {
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
     * @deprecated please use {@link ExcelWriter#write(List, Sheet)}
     */
    @Deprecated
    public ExcelWriter write1(List data, Sheet sheet) {
        return write(data, sheet);
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @deprecated please use {@link ExcelWriter#write(List, Sheet)}
     */
    @Deprecated
    public ExcelWriter write0(List data, Sheet sheet) {
        return write(data, sheet);
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
     * @deprecated please use {@link ExcelWriter#write(List, Sheet,Table)}
     */
    @Deprecated
    public ExcelWriter write0(List data, Sheet sheet, Table table) {
        return write(data, sheet, table);

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
     * @deprecated please use {@link ExcelWriter#write(List, Sheet,Table)}
     */
    @Deprecated
    public ExcelWriter write1(List data, Sheet sheet, Table table) {
        return write(data, sheet, table);

    }

    /**
     * Merge Cells，Indexes are zero-based.
     *
     * @param firstRow
     *            Index of first row
     * @param lastRow
     *            Index of last row (inclusive), must be equal to or larger than {@code firstRow}
     * @param firstCol
     *            Index of first column
     * @param lastCol
     *            Index of last column (inclusive), must be equal to or larger than {@code firstCol}
     * @deprecated please use{@link OnceAbsoluteMergeStrategy}
     */
    @Deprecated
    public ExcelWriter merge(int firstRow, int lastRow, int firstCol, int lastCol) {
        excelBuilder.merge(firstRow, lastRow, firstCol, lastCol);
        return this;
    }

    /**
     * Close IO
     */
    public void finish() {
        excelBuilder.finish();
    }

}
