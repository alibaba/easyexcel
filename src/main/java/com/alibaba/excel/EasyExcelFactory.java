package com.alibaba.excel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterTableBuilder;

/**
 * Reader and writer factory class
 *
 * @author jipengfei
 */
public class EasyExcelFactory {

    /**
     * Quickly read small files，no more than 10,000 lines.
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream.
     * @param sheet
     *            read sheet.
     * @return analysis result.
     */
    public static List<Object> read(InputStream in, Sheet sheet) {
        final List<Object> rows = new ArrayList<Object>();
        new ExcelReader(in, null, new AnalysisEventListener<Object>() {
            @Override
            public void invoke(Object object, AnalysisContext context) {
                rows.add(object);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {}
        }, false).read(sheet);
        return rows;
    }

    /**
     * Parsing large file
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream.
     * @param sheet
     *            read sheet.
     * @param listener
     *            Callback method after each row is parsed.
     */
    public static void readBySax(InputStream in, Sheet sheet, AnalysisEventListener listener) {
        new ExcelReader(in, null, listener).read(sheet);
    }

    /**
     * Get ExcelReader.
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream.
     * @param listener
     *            Callback method after each row is parsed.
     * @return ExcelReader.
     */
    public static ExcelReader getReader(InputStream in, AnalysisEventListener listener) {
        return new ExcelReader(in, null, listener);
    }

    /**
     * Get ExcelWriter
     *
     * @param outputStream
     *            the java OutputStream you wish to write the value to.
     * @return new ExcelWriter.
     * @deprecated please use {@link EasyExcelFactory#writerBuilder()}
     */
    @Deprecated
    public static ExcelWriter getWriter(OutputStream outputStream) {
        return writerBuilder().outputFile(outputStream).autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE)
            .build();
    }

    /**
     * Get ExcelWriter
     *
     * @param outputStream
     *            the java OutputStream you wish to write the value to.
     * @param typeEnum
     *            03 or 07
     * @param needHead
     *            Do you need to write the header to the file?
     * @return new ExcelWriter
     * @deprecated please use {@link EasyExcelFactory#writerBuilder()}
     */
    @Deprecated
    public static ExcelWriter getWriter(OutputStream outputStream, ExcelTypeEnum typeEnum, boolean needHead) {
        return writerBuilder().outputFile(outputStream).excelType(typeEnum).needHead(needHead)
            .autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).build();
    }

    /**
     * Get ExcelWriter with a template file
     *
     * @param temp
     *            Append value after a POI file , Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @return new ExcelWriter
     * @deprecated please use {@link EasyExcelFactory#writerBuilder()}
     */
    @Deprecated
    public static ExcelWriter getWriterWithTemp(InputStream temp, OutputStream outputStream, ExcelTypeEnum typeEnum,
        boolean needHead) {
        return writerBuilder().withTemplate(temp).outputFile(outputStream).excelType(typeEnum).needHead(needHead)
            .autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).build();
    }

    /**
     * Get ExcelWriter with a template file
     *
     * @param temp
     *            Append value after a POI file , Can be null（the template POI filesystem that contains the Workbook
     *            stream)
     * @param outputStream
     *            the java OutputStream you wish to write the value to
     * @param typeEnum
     *            03 or 07
     * @param needHead
     * @param handler
     *            User-defined callback
     * @return new ExcelWriter
     * @deprecated please use {@link EasyExcelFactory#writerBuilder()}
     */
    @Deprecated
    public static ExcelWriter getWriterWithTempAndHandler(InputStream temp, OutputStream outputStream,
        ExcelTypeEnum typeEnum, boolean needHead, WriteHandler handler) {
        return writerBuilder().withTemplate(temp).outputFile(outputStream).excelType(typeEnum).needHead(needHead)
            .registerWriteHandler(handler).autoCloseStream(Boolean.FALSE).convertAllFiled(Boolean.FALSE).build();
    }

    public static ExcelWriterBuilder writerBuilder() {
        return new ExcelWriterBuilder();
    }

    public static ExcelWriterSheetBuilder writerSheetBuilder() {
        return new ExcelWriterSheetBuilder();
    }

    public static ExcelWriterTableBuilder writerTableBuilder() {
        return new ExcelWriterTableBuilder();
    }
}
