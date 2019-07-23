package com.alibaba.excel.read.metadata;

import java.io.File;
import java.io.InputStream;

import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Workbook
 *
 * @author zhuangjiaju
 **/
public class ReadWorkbook extends ReadBasicParameter {
    /**
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * Read InputStream
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private InputStream inputStream;
    /**
     * Read file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    private File file;
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

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
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
}
