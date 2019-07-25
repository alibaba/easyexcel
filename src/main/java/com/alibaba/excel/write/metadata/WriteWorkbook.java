package com.alibaba.excel.write.metadata;

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
public class WriteWorkbook extends WriteBasicParameter {
    /**
     * Excel type.The default is xlsx
     */
    private ExcelTypeEnum excelType;
    /**
     * Final output stream
     */
    private OutputStream outputStream;
    /**
     * <li>write: Template input stream
     * <li>read: Read InputStream
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private InputStream templateInputStream;

    /**
     * <li>write: Template file
     * <li>read: Read file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private File templateFile;
    /**
     * Default true
     */
    private Boolean autoCloseStream;
    /**
     * Mandatory use 'inputStream' .Default is false
     */
    private Boolean mandatoryUseInputStream;
    /**
     * The default is all excel objects.Default is true.
     * <li>if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a field.
     * <li>if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
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

    public InputStream getTemplateInputStream() {
        return templateInputStream;
    }

    public void setTemplateInputStream(InputStream templateInputStream) {
        this.templateInputStream = templateInputStream;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public void setTemplateFile(File templateFile) {
        this.templateFile = templateFile;
    }

    public Boolean getAutoCloseStream() {
        return autoCloseStream;
    }

    public void setAutoCloseStream(Boolean autoCloseStream) {
        this.autoCloseStream = autoCloseStream;
    }

    public Boolean getMandatoryUseInputStream() {
        return mandatoryUseInputStream;
    }

    public void setMandatoryUseInputStream(Boolean mandatoryUseInputStream) {
        this.mandatoryUseInputStream = mandatoryUseInputStream;
    }

    public Boolean getConvertAllFiled() {
        return convertAllFiled;
    }

    public void setConvertAllFiled(Boolean convertAllFiled) {
        this.convertAllFiled = convertAllFiled;
    }

    public com.alibaba.excel.event.WriteHandler getWriteHandler() {
        return writeHandler;
    }

    public void setWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {
        this.writeHandler = writeHandler;
    }
}
