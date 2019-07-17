package com.alibaba.excel.metadata;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * Workbook
 *
 * @author zhuangjiaju
 **/
public class Workbook extends BasicParameter {
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;

    /**
     * Read final output stream
     */
    private OutputStream outputStream;
    /**
     * <li>write: Template input stream
     * <li>read: Read InputStream
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private InputStream inputStream;

    /**
     * <li>write: Template file
     * <li>read: Read file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private File file;
    /**
     * Default true
     */
    private Boolean autoCloseStream;
    /**
     * The default is all excel objects.if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a
     * field. if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
     * 
     * @deprecated Just to be compatible with historical data, The default is always going to be convert all filed.
     */
    @Deprecated
    private Boolean convertAllFiled;
    /**
     * Write handler
     * 
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    private com.alibaba.excel.event.WriteHandler writeHandler;

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public com.alibaba.excel.event.WriteHandler getWriteHandler() {
        return writeHandler;
    }

    public void setWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {
        this.writeHandler = writeHandler;
    }

    public Boolean getAutoCloseStream() {
        return autoCloseStream;
    }

    public void setAutoCloseStream(Boolean autoCloseStream) {
        this.autoCloseStream = autoCloseStream;
    }

    public Boolean getConvertAllFiled() {
        return convertAllFiled;
    }

    public void setConvertAllFiled(Boolean convertAllFiled) {
        this.convertAllFiled = convertAllFiled;
    }
}
