package com.alibaba.excel.read.builder;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.cache.selector.ReadCacheSelector;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ModelBuildEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Build ExcelWriter
 *
 * @author Jiaju Zhuang
 */
public class ExcelReaderBuilder {
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
     * Count the number of added heads when read sheet.
     *
     * <p>
     * 0 - This Sheet has no head ,since the first row are the data
     * <p>
     * 1 - This Sheet has one row head , this is the default
     * <p>
     * 2 - This Sheet has two row head ,since the third row is the data
     *
     * @param headRowNumber
     * @return
     */
    public ExcelReaderBuilder headRowNumber(Integer headRowNumber) {
        readWorkbook.setHeadRowNumber(headRowNumber);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelReaderBuilder#head(List)} and {@link ExcelReaderBuilder#head(Class)}
     *
     * @param head
     * @return
     */
    public ExcelReaderBuilder head(List<List<String>> head) {
        readWorkbook.setHead(head);
        return this;
    }

    /**
     * You can only choose one of the {@link ExcelReaderBuilder#head(List)} and {@link ExcelReaderBuilder#head(Class)}
     *
     * @param clazz
     * @return
     */
    public ExcelReaderBuilder head(Class clazz) {
        readWorkbook.setClazz(clazz);
        return this;
    }

    /**
     * Custom type conversions override the default.
     *
     * @param converter
     * @return
     */
    public ExcelReaderBuilder registerConverter(Converter converter) {
        if (readWorkbook.getCustomConverterList() == null) {
            readWorkbook.setCustomConverterList(new ArrayList<Converter>());
        }
        readWorkbook.getCustomConverterList().add(converter);
        return this;
    }

    /**
     * Custom type listener run after default
     *
     * @param readListener
     * @return
     */
    public ExcelReaderBuilder registerReadListener(ReadListener readListener) {
        if (readWorkbook.getCustomReadListenerList() == null) {
            readWorkbook.setCustomReadListenerList(new ArrayList<ReadListener>());
        }
        readWorkbook.getCustomReadListenerList().add(readListener);
        return this;
    }

    /**
     * true if date uses 1904 windowing, or false if using 1900 date windowing.
     *
     * default is false
     *
     * @param use1904windowing
     * @return
     */
    public ExcelReaderBuilder use1904windowing(Boolean use1904windowing) {
        readWorkbook.setUse1904windowing(use1904windowing);
        return this;
    }

    /**
     * Automatic trim includes sheet name and content
     *
     * @param autoTrim
     * @return
     */
    public ExcelReaderBuilder autoTrim(Boolean autoTrim) {
        readWorkbook.setAutoTrim(autoTrim);
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

    public ExcelReader doReadAll() {
        ExcelReader excelReader = build();
        excelReader.readAll();
        excelReader.finish();
        return excelReader;
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
}
