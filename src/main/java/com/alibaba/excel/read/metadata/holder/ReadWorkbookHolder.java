package com.alibaba.excel.read.metadata.holder;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.cache.selector.EternalReadCacheSelector;
import com.alibaba.excel.cache.selector.ReadCacheSelector;
import com.alibaba.excel.cache.selector.SimpleReadCacheSelector;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.enums.HolderEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Workbook holder
 *
 * @author Jiaju Zhuang
 */
@Data
@NoArgsConstructor
public class ReadWorkbookHolder extends AbstractReadHolder {

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
     * Whether the encryption
     */
    private String password;
    /**
     * Read some additional fields. None are read by default.
     *
     * @see CellExtraTypeEnum
     */
    private Set<CellExtraTypeEnum> extraReadSet;
    /**
     * Actual sheet data
     */
    private List<ReadSheet> actualSheetDataList;
    /**
     * Parameter sheet data
     */
    private List<ReadSheet> parameterSheetDataList;
    /**
     * Read all
     */
    private Boolean readAll;

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

    public ReadWorkbookHolder(ReadWorkbook readWorkbook) {
        super(readWorkbook, null, readWorkbook.getConvertAllFiled());
        this.readWorkbook = readWorkbook;
        if (readWorkbook.getInputStream() != null) {
            this.inputStream = readWorkbook.getInputStream();
        }
        this.file = readWorkbook.getFile();
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
        if (readWorkbook.getExtraReadSet() == null) {
            this.extraReadSet = new HashSet<CellExtraTypeEnum>();
        } else {
            this.extraReadSet = readWorkbook.getExtraReadSet();
        }
        this.hasReadSheet = new HashSet<Integer>();
        this.password = readWorkbook.getPassword();
    }


    @Override
    public HolderEnum holderType() {
        return HolderEnum.WORKBOOK;
    }
}
