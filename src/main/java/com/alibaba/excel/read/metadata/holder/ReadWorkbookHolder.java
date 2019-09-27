package com.alibaba.excel.read.metadata.holder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.cache.selector.EternalReadCacheSelector;
import com.alibaba.excel.cache.selector.ReadCacheSelector;
import com.alibaba.excel.cache.selector.SimpleReadCacheSelector;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
public class ReadWorkbookHolder extends AbstractReadHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadWorkbookHolder.class);

    /**
     * current param
     */
    private ReadWorkbook readWorkbook;
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
     * Excel type
     */
    private ExcelTypeEnum excelType;
    /**
     * This object can be read in the Listener {@link AnalysisEventListener#invoke(Object, AnalysisContext)}
     * {@link AnalysisContext#getCustom()}
     *
     */
    private Object customObject;
    /**
     * Ignore empty rows.Default is true.
     */
    private Boolean ignoreEmptyRow;
    /**
     * A cache that stores temp data to save memory.
     */
    private ReadCache readCache;
    /**
     * Select the cache.Default use {@link com.alibaba.excel.cache.selector.SimpleReadCacheSelector}
     */
    private ReadCacheSelector readCacheSelector;
    /**
     * Temporary files when reading excel
     */
    private File tempFile;

    /**
     * The default is all excel objects.if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a
     * field. if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
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

    /**
     * Prevent repeating sheet
     */
    private Set<Integer> hasReadSheet;
    /**
     * Package
     */
    private OPCPackage opcPackage;
    /**
     * File System
     */
    private POIFSFileSystem poifsFileSystem;

    /**
     * Excel 2003 cannot read specific sheet. It can only read sheet by sheet.So when you specify one sheet, you ignore
     * the others.
     */
    private Boolean ignoreRecord03;

    public ReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook, null, readWorkbook.getConvertAllFiled());
        this.readWorkbook = readWorkbook;
        if (readWorkbook.getInputStream() != null) {
            if (readWorkbook.getInputStream().markSupported()) {
                this.inputStream = readWorkbook.getInputStream();
            } else {
                this.inputStream = new BufferedInputStream(readWorkbook.getInputStream());
            }
        }
        this.file = readWorkbook.getFile();
        if (file == null && inputStream == null) {
            throw new ExcelAnalysisException("File and inputStream must be a non-null.");
        }
        if (readWorkbook.getMandatoryUseInputStream() == null) {
            this.mandatoryUseInputStream = Boolean.FALSE;
        } else {
            this.mandatoryUseInputStream = readWorkbook.getMandatoryUseInputStream();
        }
        if (readWorkbook.getAutoCloseStream() == null) {
            this.autoCloseStream = Boolean.TRUE;
        } else {
            this.autoCloseStream = readWorkbook.getAutoCloseStream();
        }
        if (readWorkbook.getExcelType() == null) {
            this.excelType = ExcelTypeEnum.valueOf(file, inputStream);
        } else {
            this.excelType = readWorkbook.getExcelType();
        }
        if (ExcelTypeEnum.XLS == excelType && getGlobalConfiguration().getUse1904windowing() == null) {
            getGlobalConfiguration().setUse1904windowing(Boolean.FALSE);
        }
        this.customObject = readWorkbook.getCustomObject();
        if (readWorkbook.getIgnoreEmptyRow() == null) {
            this.ignoreEmptyRow = Boolean.TRUE;
        } else {
            this.ignoreEmptyRow = readWorkbook.getIgnoreEmptyRow();
        }
        if (readWorkbook.getReadCache() != null) {
            if (readWorkbook.getReadCacheSelector() != null) {
                throw new ExcelAnalysisException("'readCache' and 'readCacheSelector' only one choice.");
            }
            this.readCacheSelector = new EternalReadCacheSelector(readWorkbook.getReadCache());
        } else {
            if (readWorkbook.getReadCacheSelector() == null) {
                this.readCacheSelector = new SimpleReadCacheSelector();
            } else {
                this.readCacheSelector = readWorkbook.getReadCacheSelector();
            }
        }
        if (readWorkbook.getDefaultReturnMap() == null) {
            this.defaultReturnMap = Boolean.TRUE;
        } else {
            this.defaultReturnMap = readWorkbook.getDefaultReturnMap();
        }
        this.hasReadSheet = new HashSet<Integer>();
        this.ignoreRecord03 = Boolean.FALSE;
    }

    public ReadWorkbook getReadWorkbook() {
        return readWorkbook;
    }

    public void setReadWorkbook(ReadWorkbook readWorkbook) {
        this.readWorkbook = readWorkbook;
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

    public ExcelTypeEnum getExcelType() {
        return excelType;
    }

    public void setExcelType(ExcelTypeEnum excelType) {
        this.excelType = excelType;
    }

    public Object getCustomObject() {
        return customObject;
    }

    public void setCustomObject(Object customObject) {
        this.customObject = customObject;
    }

    public Boolean getIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    public void setIgnoreEmptyRow(Boolean ignoreEmptyRow) {
        this.ignoreEmptyRow = ignoreEmptyRow;
    }

    public ReadCache getReadCache() {
        return readCache;
    }

    public void setReadCache(ReadCache readCache) {
        this.readCache = readCache;
    }

    public ReadCacheSelector getReadCacheSelector() {
        return readCacheSelector;
    }

    public void setReadCacheSelector(ReadCacheSelector readCacheSelector) {
        this.readCacheSelector = readCacheSelector;
    }

    public Boolean getMandatoryUseInputStream() {
        return mandatoryUseInputStream;
    }

    public void setMandatoryUseInputStream(Boolean mandatoryUseInputStream) {
        this.mandatoryUseInputStream = mandatoryUseInputStream;
    }

    public File getTempFile() {
        return tempFile;
    }

    public void setTempFile(File tempFile) {
        this.tempFile = tempFile;
    }

    public Boolean getConvertAllFiled() {
        return convertAllFiled;
    }

    public void setConvertAllFiled(Boolean convertAllFiled) {
        this.convertAllFiled = convertAllFiled;
    }

    public Set<Integer> getHasReadSheet() {
        return hasReadSheet;
    }

    public void setHasReadSheet(Set<Integer> hasReadSheet) {
        this.hasReadSheet = hasReadSheet;
    }

    public Boolean getDefaultReturnMap() {
        return defaultReturnMap;
    }

    public void setDefaultReturnMap(Boolean defaultReturnMap) {
        this.defaultReturnMap = defaultReturnMap;
    }

    public OPCPackage getOpcPackage() {
        return opcPackage;
    }

    public void setOpcPackage(OPCPackage opcPackage) {
        this.opcPackage = opcPackage;
    }

    public POIFSFileSystem getPoifsFileSystem() {
        return poifsFileSystem;
    }

    public void setPoifsFileSystem(POIFSFileSystem poifsFileSystem) {
        this.poifsFileSystem = poifsFileSystem;
    }

    public Boolean getIgnoreRecord03() {
        return ignoreRecord03;
    }

    public void setIgnoreRecord03(Boolean ignoreRecord03) {
        this.ignoreRecord03 = ignoreRecord03;
    }

    @Override
    public HolderEnum holderType() {
        return HolderEnum.WORKBOOK;
    }
}
