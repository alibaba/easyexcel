package com.alibaba.excel.read.metadata;

import java.io.File;
import java.io.InputStream;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
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
     * Mandatory use 'inputStream' .Default is false.
     * <li>if false,Will transfer 'inputStream' to temporary files to improve efficiency
     */
    private Boolean mandatoryUseInputStream;
    /**
     * Default true
     */
    private Boolean autoCloseStream;
    /**
     * This object can be read in the Listener {@link AnalysisEventListener#invoke(Object, AnalysisContext)}
     * {@link AnalysisContext#getCustom()}
     *
     */
    private Object customObject;
    /**
     * A cache that stores temp data to save memory.Default use {@link com.alibaba.excel.cache.Ehcache}
     */
    private ReadCache readCache;
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

    public Object getCustomObject() {
        return customObject;
    }

    public void setCustomObject(Object customObject) {
        this.customObject = customObject;
    }

    public Boolean getMandatoryUseInputStream() {
        return mandatoryUseInputStream;
    }

    public void setMandatoryUseInputStream(Boolean mandatoryUseInputStream) {
        this.mandatoryUseInputStream = mandatoryUseInputStream;
    }

    public ReadCache getReadCache() {
        return readCache;
    }

    public void setReadCache(ReadCache readCache) {
        this.readCache = readCache;
    }

    public Boolean getConvertAllFiled() {
        return convertAllFiled;
    }

    public void setConvertAllFiled(Boolean convertAllFiled) {
        this.convertAllFiled = convertAllFiled;
    }
}
