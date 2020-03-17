package com.alibaba.excel.read.builder;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.cache.selector.ReadCacheSelector;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.read.listener.ModelBuildEventListener;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Build ExcelWriter
 *
 * @author Jiaju Zhuang
 */
public class ExcelReaderBuilder extends AbstractExcelReaderParameterBuilder<ExcelReaderBuilder, ReadWorkbook> {
    /**
     * Workbook
     */
    private ReadWorkbook readWorkbook;

    public ExcelReaderBuilder() {
        this.readWorkbook = new ReadWorkbook();
    }

    public ExcelReaderBuilder excelType(ExcelTypeEnum excelType) {
        readWorkbook.setExcelType(excelType);
        return this;
    }

    /**
     * Read InputStream
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    public ExcelReaderBuilder file(InputStream inputStream) {
        readWorkbook.setInputStream(inputStream);
        return this;
    }

    /**
     * Read file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    public ExcelReaderBuilder file(File file) {
        readWorkbook.setFile(file);
        return this;
    }

    /**
     * Read file
     * <p>
     * If 'inputStream' and 'file' all not empty,file first
     */
    public ExcelReaderBuilder file(String pathName) {
        return file(new File(pathName));
    }

    /**
     * Mandatory use 'inputStream' .Default is false.
     * <p>
     * if false,Will transfer 'inputStream' to temporary files to improve efficiency
     */
    public ExcelReaderBuilder mandatoryUseInputStream(Boolean mandatoryUseInputStream) {
        readWorkbook.setMandatoryUseInputStream(mandatoryUseInputStream);
        return this;
    }

    /**
     * Default true
     *
     * @param autoCloseStream
     * @return
     */
    public ExcelReaderBuilder autoCloseStream(Boolean autoCloseStream) {
        readWorkbook.setAutoCloseStream(autoCloseStream);
        return this;
    }

    /**
     * Ignore empty rows.Default is true.
     *
     * @param ignoreEmptyRow
     * @return
     */
    public ExcelReaderBuilder ignoreEmptyRow(Boolean ignoreEmptyRow) {
        readWorkbook.setIgnoreEmptyRow(ignoreEmptyRow);
        return this;
    }

    /**
     * This object can be read in the Listener {@link AnalysisEventListener#invoke(Object, AnalysisContext)}
     * {@link AnalysisContext#getCustom()}
     *
     * @param customObject
     * @return
     */
    public ExcelReaderBuilder customObject(Object customObject) {
        readWorkbook.setCustomObject(customObject);
        return this;
    }

    /**
     * A cache that stores temp data to save memory.
     *
     * @param readCache
     * @return
     */
    public ExcelReaderBuilder readCache(ReadCache readCache) {
        readWorkbook.setReadCache(readCache);
        return this;
    }

    /**
     * Select the cache.Default use {@link com.alibaba.excel.cache.selector.SimpleReadCacheSelector}
     *
     * @param readCacheSelector
     * @return
     */
    public ExcelReaderBuilder readCacheSelector(ReadCacheSelector readCacheSelector) {
        readWorkbook.setReadCacheSelector(readCacheSelector);
        return this;
    }

    /**
     * Whether the encryption
     *
     * @param password
     * @return
     */
    public ExcelReaderBuilder password(String password) {
        readWorkbook.setPassword(password);
        return this;
    }

    /**
     * SAXParserFactory used when reading xlsx.
     * <p>
     * The default will automatically find.
     * <p>
     * Please pass in the name of a class ,like : "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
     *
     * @see SAXParserFactory#newInstance()
     * @see SAXParserFactory#newInstance(String, ClassLoader)
     * @param xlsxSAXParserFactoryName
     * @return
     */
    public ExcelReaderBuilder xlsxSAXParserFactoryName(String xlsxSAXParserFactoryName) {
        readWorkbook.setXlsxSAXParserFactoryName(xlsxSAXParserFactoryName);
        return this;
    }

    /**
     * Read some extra information, not by default
     *
     * @param extraType
     *            extra information type
     * @return
     */
    public ExcelReaderBuilder extraRead(CellExtraTypeEnum extraType) {
        if (readWorkbook.getExtraReadSet() == null) {
            readWorkbook.setExtraReadSet(new HashSet<CellExtraTypeEnum>());
        }
        readWorkbook.getExtraReadSet().add(extraType);
        return this;
    }

    /**
     * Whether to use the default listener, which is used by default.
     * <p>
     * The {@link ModelBuildEventListener} is loaded by default to convert the object.
     *
     * @param useDefaultListener
     * @return
     */
    public ExcelReaderBuilder useDefaultListener(Boolean useDefaultListener) {
        readWorkbook.setUseDefaultListener(useDefaultListener);
        return this;
    }

    public ExcelReader build() {
        return new ExcelReader(readWorkbook);
    }

    public void doReadAll() {
        ExcelReader excelReader = build();
        excelReader.readAll();
        excelReader.finish();
    }

    /**
     * Synchronous reads return results
     *
     * @return
     */
    public <T> List<T> doReadAllSync() {
        SyncReadListener syncReadListener = new SyncReadListener();
        registerReadListener(syncReadListener);
        ExcelReader excelReader = build();
        excelReader.readAll();
        excelReader.finish();
        return (List<T>)syncReadListener.getList();
    }

    public ExcelReaderSheetBuilder sheet() {
        return sheet(null, null);
    }

    public ExcelReaderSheetBuilder sheet(Integer sheetNo) {
        return sheet(sheetNo, null);
    }

    public ExcelReaderSheetBuilder sheet(String sheetName) {
        return sheet(null, sheetName);
    }

    public ExcelReaderSheetBuilder sheet(Integer sheetNo, String sheetName) {
        ExcelReaderSheetBuilder excelReaderSheetBuilder = new ExcelReaderSheetBuilder(build());
        if (sheetNo != null) {
            excelReaderSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            excelReaderSheetBuilder.sheetName(sheetName);
        }
        return excelReaderSheetBuilder;
    }

    @Override
    protected ReadWorkbook parameter() {
        return readWorkbook;
    }
}
