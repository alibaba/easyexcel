package com.alibaba.excel;

import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.parameter.GenerateParam;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Excel Writer This tool is used to write data out to Excel via POI.
 * This object can perform the following two functions.
 * <pre>
 *    1. Create a new empty Excel workbook, write the data to the stream after the data is filled.
 *    2. Edit existing Excel, write the original Excel file, or write it to other places.}
 * </pre>
 * @author jipengfei
 */
public class ExcelWriter {

    private ExcelBuilder excelBuilder;

    /**
     * Create new writer
     * @param outputStream the java OutputStream you wish to write the data to
     * @param typeEnum 03 or 07
     */
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum) {
        this(outputStream, typeEnum, true);
    }

    @Deprecated
    private Class<? extends BaseRowModel> objectClass;

    /**
     * @param generateParam
     */
    @Deprecated
    public ExcelWriter(GenerateParam generateParam) {
        this(generateParam.getOutputStream(), generateParam.getType(), true);
        this.objectClass = generateParam.getClazz();
    }

    /**
     *
     * Create new writer
     * @param outputStream the java OutputStream you wish to write the data to
     * @param typeEnum 03 or 07
     * @param needHead Do you need to write the header to the file?
     */
    public ExcelWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        excelBuilder = new ExcelBuilderImpl(null, outputStream, typeEnum, needHead, null);
    }

    /**
     *  Create new writer
     * @param templateInputStream Append data after a POI file ,Can be null（the template POI filesystem that contains the Workbook stream)
     * @param outputStream the java OutputStream you wish to write the data to
     * @param typeEnum 03 or 07
     */
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum,Boolean needHead) {
        excelBuilder = new ExcelBuilderImpl(templateInputStream,outputStream, typeEnum, needHead, null);
    }


    /**
     *  Create new writer
     * @param templateInputStream Append data after a POI file ,Can be null（the template POI filesystem that contains the Workbook stream)
     * @param outputStream the java OutputStream you wish to write the data to
     * @param typeEnum 03 or 07
     * @param writeHandler User-defined callback
     */
    public ExcelWriter(InputStream templateInputStream, OutputStream outputStream, ExcelTypeEnum typeEnum, Boolean needHead,
                       WriteHandler writeHandler) {
        excelBuilder = new ExcelBuilderImpl(templateInputStream,outputStream, typeEnum, needHead,writeHandler);
    }

    /**
     * Write data to a sheet
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @return this current writer
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }


    /**
     * Write data to a sheet
     * @param data Data to be written
     * @return this current writer
     */
    @Deprecated
    public ExcelWriter write(List data) {
        if (objectClass != null) {
            return this.write(data,new Sheet(1,0,objectClass));
        }else {
            return this.write0(data,new Sheet(1,0,objectClass));

        }
    }

    /**
     *
     * Write data to a sheet
     * @param data Data to be written
     * @param sheet Write to this sheet
     * @return this
     */
    public ExcelWriter write1(List<List<Object>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write data to a sheet
     * @param data  Data to be written
     * @param sheet Write to this sheet
     * @return this
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet) {
        excelBuilder.addContent(data, sheet);
        return this;
    }

    /**
     * Write data to a sheet
     * @param data  Data to be written
     * @param sheet Write to this sheet
     * @param table Write to this table
     * @return this
     */
    public ExcelWriter write(List<? extends BaseRowModel> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Write data to a sheet
     * @param data  Data to be written
     * @param sheet Write to this sheet
     * @param table Write to this table
     * @return this
     */
    public ExcelWriter write0(List<List<String>> data, Sheet sheet, Table table) {
        excelBuilder.addContent(data, sheet, table);
        return this;
    }

    /**
     * Merge Cells，Indexes are zero-based.
     *
     * @param firstRow Index of first row
     * @param lastRow Index of last row (inclusive), must be equal to or larger than {@code firstRow}
     * @param firstCol Index of first column
     * @param lastCol Index of last column (inclusive), must be equal to or larger than {@code firstCol}
     */
    public ExcelWriter merge(int firstRow, int lastRow, int firstCol, int lastCol){
        excelBuilder.merge(firstRow,lastRow,firstCol,lastCol);
        return this;
    }

    /**
     * Write data to a sheet
     * @param data  Data to be written
     * @param sheet Write to this sheet
     * @param table Write to this table
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
