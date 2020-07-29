package com.alibaba.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.WriteContext;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.parameter.GenerateParam;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.ExcelBuilder;
import com.alibaba.excel.write.ExcelBuilderImpl;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.alibaba.excel.write.metadata.fill.FillConfig;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriter.class);

    private ExcelBuilder excelBuilder;

    /**
     * Create new writer
     *
     * @param writeWorkbook
     */
    public ExcelWriter(WriteWorkbook writeWorkbook) {
        excelBuilder = new ExcelBuilderImpl(writeWorkbook);
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
        this(null, outputStream, typeEnum, needHead, null);
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
        this(templateInputStream, outputStream, typeEnum, needHead, null);
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
        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setTemplateInputStream(templateInputStream);
        writeWorkbook.setOutputStream(outputStream);
        writeWorkbook.setExcelType(typeEnum);
        writeWorkbook.setNeedHead(needHead);
        writeWorkbook.setCustomWriteHandlerList(customWriteHandlerList);
        excelBuilder = new ExcelBuilderImpl(writeWorkbook);
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
     * @param writeSheet
     *            Write to this sheet
     * @return this current writer
     */
    public ExcelWriter write(List data, WriteSheet writeSheet) {
        return write(data, writeSheet, null);
    }

    /**
     * Write value to a sheet
     *
     * @param data
     *            Data to be written
     * @param writeSheet
     *            Write to this sheet
     * @param writeTable
     *            Write to this table
     * @return this
     */
    public ExcelWriter write(List data, WriteSheet writeSheet, WriteTable writeTable) {
        excelBuilder.addContent(data, writeSheet, writeTable);
        return this;
    }

    /**
     * Fill value to a sheet
     *
     * @param data
     * @param writeSheet
     * @return
     */
    public ExcelWriter fill(Object data, WriteSheet writeSheet) {
        return fill(data, null, writeSheet);
    }

    /**
     * Fill value to a sheet
     *
     * @param data
     * @param fillConfig
     * @param writeSheet
     * @return
     */
    public ExcelWriter fill(Object data, FillConfig fillConfig, WriteSheet writeSheet) {
        excelBuilder.fill(data, fillConfig, writeSheet);
        return this;
    }

    /**
     * Write data to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @return this current writer
     * @deprecated please use {@link ExcelWriter#write(List, WriteSheet)}
     */
    @Deprecated
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
     * @deprecated * @deprecated please use {@link ExcelWriter#write(List, WriteSheet,WriteTable)}
     */
    @Deprecated
    public ExcelWriter write(List data, Sheet sheet, Table table) {
        WriteSheet writeSheet = null;
        if (sheet != null) {
            writeSheet = new WriteSheet();
            writeSheet.setSheetNo(sheet.getSheetNo() - 1);
            writeSheet.setSheetName(sheet.getSheetName());
            writeSheet.setClazz(sheet.getClazz());
            writeSheet.setHead(sheet.getHead());
            writeSheet.setTableStyle(sheet.getTableStyle());
            writeSheet.setRelativeHeadRowIndex(sheet.getStartRow());
            writeSheet.setColumnWidthMap(sheet.getColumnWidthMap());
        }

        WriteTable writeTable = null;
        if (table != null) {
            writeTable = new WriteTable();
            writeTable.setTableNo(table.getTableNo());
            writeTable.setClazz(table.getClazz());
            writeTable.setHead(table.getHead());
            writeTable.setTableStyle(table.getTableStyle());
        }
        return write(data, writeSheet, writeTable);
    }

    /**
     * Write data to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @return this current writer
     * @deprecated please use {@link ExcelWriter#write(List, WriteSheet)}
     */
    @Deprecated
    public ExcelWriter write0(List data, Sheet sheet) {
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
     * @deprecated * @deprecated please use {@link ExcelWriter#write(List, WriteSheet,WriteTable)}
     */
    @Deprecated
    public ExcelWriter write0(List data, Sheet sheet, Table table) {
        return write(data, sheet, table);
    }

    /**
     * Write data to a sheet
     *
     * @param data
     *            Data to be written
     * @param sheet
     *            Write to this sheet
     * @return this current writer
     * @deprecated please use {@link ExcelWriter#write(List, WriteSheet)}
     */
    @Deprecated
    public ExcelWriter write1(List data, Sheet sheet) {
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
     * @deprecated * @deprecated please use {@link ExcelWriter#write(List, WriteSheet,WriteTable)}
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
        if (excelBuilder != null) {
            excelBuilder.finish(false);
        }
    }

    /**
     * Prevents calls to {@link #finish} from freeing the cache
     *
     */
    @Override
    protected void finalize() {
        try {
            finish();
        } catch (Throwable e) {
            LOGGER.warn("Destroy object failed", e);
        }
    }

    /**
     * The context of the entire writing process
     *
     * @return
     */
    public WriteContext writeContext() {
        return excelBuilder.writeContext();
    }
}
