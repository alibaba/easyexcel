package com.alibaba.excel.read.metadata;

import java.io.File;
import java.io.InputStream;
import java.util.Set;

import javax.xml.parsers.SAXParserFactory;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.cache.selector.ReadCacheSelector;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ModelBuildEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Workbook
 *
 * @author Jiaju Zhuang
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
     * <p>
     * if false,Will transfer 'inputStream' to temporary files to improve efficiency
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
     * A cache that stores temp data to save memory.
     */
    private ReadCache readCache;
    /**
     * Ignore empty rows.Default is true.
     */
    private Boolean ignoreEmptyRow;
    /**
     * Select the cache.Default use {@link com.alibaba.excel.cache.selector.SimpleReadCacheSelector}
     */
    private ReadCacheSelector readCacheSelector;
    /**
     * Whether the encryption
     */
    private String password;
    /**
     * SAXParserFactory used when reading xlsx.
     * <p>
     * The default will automatically find.
     * <p>
     * Please pass in the name of a class ,like : "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
     *
     * @see SAXParserFactory#newInstance()
     * @see SAXParserFactory#newInstance(String, ClassLoader)
     */
    private String xlsxSAXParserFactoryName;
    /**
     * Whether to use the default listener, which is used by default.
     * <p>
     * The {@link ModelBuildEventListener} is loaded by default to convert the object.
     */
    private Boolean useDefaultListener;
    /**
     * Read some additional fields. None are read by default.
     *
     * @see CellExtraTypeEnum
     */
    private Set<CellExtraTypeEnum> extraReadSet;
    /**
     * The default is all excel objects.Default is true.
     * <p>
     * if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a field.
     * <p>
     * if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
     *
     * @deprecated Just to be compatible with historical data, The default is always going to be convert all filed.
     */
    @Deprecated
    private Boolean convertAllFiled;

    /**
     * List is returned by default, now map is returned by default
     */
    @Deprecated
    private Boolean defaultReturnMap;

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

    public Boolean getDefaultReturnMap() {
        return defaultReturnMap;
    }

    public void setDefaultReturnMap(Boolean defaultReturnMap) {
        this.defaultReturnMap = defaultReturnMap;
    }

    public Boolean getIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    public void setIgnoreEmptyRow(Boolean ignoreEmptyRow) {
        this.ignoreEmptyRow = ignoreEmptyRow;
    }

    public ReadCacheSelector getReadCacheSelector() {
        return readCacheSelector;
    }

    public void setReadCacheSelector(ReadCacheSelector readCacheSelector) {
        this.readCacheSelector = readCacheSelector;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getXlsxSAXParserFactoryName() {
        return xlsxSAXParserFactoryName;
    }

    public void setXlsxSAXParserFactoryName(String xlsxSAXParserFactoryName) {
        this.xlsxSAXParserFactoryName = xlsxSAXParserFactoryName;
    }

    public Boolean getUseDefaultListener() {
        return useDefaultListener;
    }

    public void setUseDefaultListener(Boolean useDefaultListener) {
        this.useDefaultListener = useDefaultListener;
    }

    public Set<CellExtraTypeEnum> getExtraReadSet() {
        return extraReadSet;
    }

    public void setExtraReadSet(Set<CellExtraTypeEnum> extraReadSet) {
        this.extraReadSet = extraReadSet;
    }
}
