package com.alibaba.excel;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
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
     * Build excel the write
     *
     * @return
     */
    public static ExcelWriterBuilder write() {
        return new ExcelWriterBuilder();
    }

    /**
     * Build excel the write
     *
     * @param file File to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(File file) {
        return write(file, null);
    }

    /**
     * Build excel the write
     *
     * @param file File to write
     * @param head Annotate the class for configuration information
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(File file, Class head) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(file);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(String pathName) {
        return write(pathName, null);
    }

    /**
     * Build excel the write
     *
     * @param pathName File path to write
     * @param head     Annotate the class for configuration information
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(String pathName, Class head) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(pathName);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(OutputStream outputStream) {
        return write(outputStream, null);
    }

    /**
     * Build excel the write
     *
     * @param outputStream Output stream to write
     * @param head         Annotate the class for configuration information.
     * @return Excel writer builder
     */
    public static ExcelWriterBuilder write(OutputStream outputStream, Class head) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream);
        if (head != null) {
            excelWriterBuilder.head(head);
        }
        return excelWriterBuilder;
    }

    /**
     * Build excel the <code>writerSheet</code>
     *
     * @return Excel sheet writer builder
     */
    public static ExcelWriterSheetBuilder writerSheet() {
        return writerSheet(null, null);
    }

    /**
     * Build excel the <code>writerSheet</code>
     *
     * @param sheetNo Index of sheet,0 base.
     * @return Excel sheet writer builder.
     */
    public static ExcelWriterSheetBuilder writerSheet(Integer sheetNo) {
        return writerSheet(sheetNo, null);
    }

    /**
     * Build excel the 'writerSheet'
     *
     * @param sheetName The name of sheet.
     * @return Excel sheet writer builder.
     */
    public static ExcelWriterSheetBuilder writerSheet(String sheetName) {
        return writerSheet(null, sheetName);
    }

    /**
     * Build excel the 'writerSheet'
     *
     * @param sheetNo   Index of sheet,0 base.
     * @param sheetName The name of sheet.
     * @return Excel sheet writer builder.
     */
    public static ExcelWriterSheetBuilder writerSheet(Integer sheetNo, String sheetName) {
        ExcelWriterSheetBuilder excelWriterSheetBuilder = new ExcelWriterSheetBuilder();
        if (sheetNo != null) {
            excelWriterSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            excelWriterSheetBuilder.sheetName(sheetName);
        }
        return excelWriterSheetBuilder;
    }

    /**
     * Build excel the <code>writerTable</code>
     *
     * @return Excel table writer builder.
     */
    public static ExcelWriterTableBuilder writerTable() {
        return writerTable(null);
    }

    /**
     * Build excel the 'writerTable'
     *
     * @param tableNo Index of table,0 base.
     * @return Excel table writer builder.
     */
    public static ExcelWriterTableBuilder writerTable(Integer tableNo) {
        ExcelWriterTableBuilder excelWriterTableBuilder = new ExcelWriterTableBuilder();
        if (tableNo != null) {
            excelWriterTableBuilder.tableNo(tableNo);
        }
        return excelWriterTableBuilder;
    }

    /**
     * Build excel the read
     *
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read() {
        return new ExcelReaderBuilder();
    }

    /**
     * Build excel the read
     *
     * @param file File to read.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(File file) {
        return read(file, null, null);
    }

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(File file, ReadListener readListener) {
        return read(file, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param file         File to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(File file, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(file);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param pathName File path to read.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(String pathName) {
        return read(pathName, null, null);
    }

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(String pathName, ReadListener readListener) {
        return read(pathName, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param pathName     File path to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(String pathName, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(pathName);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the read
     *
     * @param inputStream Input stream to read.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(InputStream inputStream) {
        return read(inputStream, null, null);
    }

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(InputStream inputStream, ReadListener readListener) {
        return read(inputStream, null, readListener);
    }

    /**
     * Build excel the read
     *
     * @param inputStream  Input stream to read.
     * @param head         Annotate the class for configuration information.
     * @param readListener Read listener.
     * @return Excel reader builder.
     */
    public static ExcelReaderBuilder read(InputStream inputStream, Class head, ReadListener readListener) {
        ExcelReaderBuilder excelReaderBuilder = new ExcelReaderBuilder();
        excelReaderBuilder.file(inputStream);
        if (head != null) {
            excelReaderBuilder.head(head);
        }
        if (readListener != null) {
            excelReaderBuilder.registerReadListener(readListener);
        }
        return excelReaderBuilder;
    }

    /**
     * Build excel the 'readSheet'
     *
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet() {
        return readSheet(null, null);
    }

    /**
     * Build excel the 'readSheet'
     *
     * @param sheetNo Index of sheet,0 base.
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet(Integer sheetNo) {
        return readSheet(sheetNo, null);
    }

    /**
     * Build excel the 'readSheet'
     *
     * @param sheetName The name of sheet.
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet(String sheetName) {
        return readSheet(null, sheetName);
    }

    /**
     * Build excel the 'readSheet'
     *
     * @param sheetNo   Index of sheet,0 base.
     * @param sheetName The name of sheet.
     * @return Excel sheet reader builder.
     */
    public static ExcelReaderSheetBuilder readSheet(Integer sheetNo, String sheetName) {
        ExcelReaderSheetBuilder excelReaderSheetBuilder = new ExcelReaderSheetBuilder();
        if (sheetNo != null) {
            excelReaderSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            excelReaderSheetBuilder.sheetName(sheetName);
        }
        return excelReaderSheetBuilder;
    }
}
