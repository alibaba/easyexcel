package com.alibaba.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.ExcelAnalyser;
import com.alibaba.excel.analysis.ExcelAnalyserImpl;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.cache.MapCache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.parameter.AnalysisParam;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Excel readers are all read in event mode.
 *
 * @author jipengfei
 */
public class ExcelReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

    /**
     * Analyser
     */
    private ExcelAnalyser excelAnalyser;

    /**
     * Create new reader
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream
     * @param excelTypeEnum
     *            03 or 07
     * @param customContent
     *            {@link AnalysisEventListener#invoke(Object, AnalysisContext) }AnalysisContext
     * @param eventListener
     *            Callback method after each row is parsed.
     * @deprecated please use {@link EasyExcelFactory#read()} build 'ExcelReader'
     */
    @Deprecated
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
        AnalysisEventListener eventListener) {
        this(in, excelTypeEnum, customContent, eventListener, true);
    }

    /**
     * Create new reader
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream
     * @param customContent
     *            {@link AnalysisEventListener#invoke(Object, AnalysisContext) }AnalysisContext
     * @param eventListener
     *            Callback method after each row is parsed
     * @deprecated please use {@link EasyExcelFactory#read()} build 'ExcelReader'
     */
    @Deprecated
    public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener) {
        this(in, customContent, eventListener, true);
    }

    /**
     * Create new reader
     *
     * @param param
     *            old param Deprecated
     * @param eventListener
     *            Callback method after each row is parsed.
     * @deprecated please use {@link EasyExcelFactory#read()} build 'ExcelReader'
     */
    @Deprecated
    public ExcelReader(AnalysisParam param, AnalysisEventListener eventListener) {
        this(param.getIn(), param.getExcelTypeEnum(), param.getCustomContent(), eventListener, true);
    }

    /**
     * Create new reader
     *
     * @param in
     * @param customContent
     *            {@link AnalysisEventListener#invoke(Object, AnalysisContext) }AnalysisContext
     * @param eventListener
     * @param trim
     *            The content of the form is empty and needs to be empty. The purpose is to be fault-tolerant, because
     *            there are often table contents with spaces that can not be converted into custom types. For example:
     *            '1234 ' contain a space cannot be converted to int.
     * @deprecated please use {@link EasyExcelFactory#read()} build 'ExcelReader'
     */
    @Deprecated
    public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener, boolean trim) {
        this(in, null, customContent, eventListener, trim);
    }

    /**
     * Create new reader
     *
     * @param in
     *            the POI filesystem that contains the Workbook stream
     * @param excelTypeEnum
     *            03 or 07
     * @param customContent
     *            {@link AnalysisEventListener#invoke(Object, AnalysisContext) }AnalysisContext
     * @param eventListener
     *            Callback method after each row is parsed.
     * @param trim
     *            The content of the form is empty and needs to be empty. The purpose is to be fault-tolerant, because
     *            there are often table contents with spaces that can not be converted into custom types. For example:
     *            '1234 ' contain a space cannot be converted to int.
     * @deprecated please use {@link EasyExcelFactory#read()} build 'ExcelReader'
     */
    @Deprecated
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
        AnalysisEventListener eventListener, boolean trim) {
        ReadWorkbook readWorkbook = new ReadWorkbook();
        readWorkbook.setInputStream(in);
        readWorkbook.setExcelType(excelTypeEnum);
        readWorkbook.setCustomObject(customContent);
        if (eventListener != null) {
            List<ReadListener> customReadListenerList = new ArrayList<ReadListener>();
            customReadListenerList.add(eventListener);
            readWorkbook.setCustomReadListenerList(customReadListenerList);
        }
        readWorkbook.setAutoTrim(trim);
        readWorkbook.setAutoCloseStream(Boolean.FALSE);
        readWorkbook.setMandatoryUseInputStream(Boolean.TRUE);
        readWorkbook.setReadCache(new MapCache());
        readWorkbook.setConvertAllFiled(Boolean.FALSE);
        readWorkbook.setDefaultReturnMap(Boolean.FALSE);
        // The previous logic was that Article 0 started reading
        readWorkbook.setHeadRowNumber(0);
        excelAnalyser = new ExcelAnalyserImpl(readWorkbook);
    }

    public ExcelReader(ReadWorkbook readWorkbook) {
        excelAnalyser = new ExcelAnalyserImpl(readWorkbook);
    }

    /**
     * Parse all sheet content by default
     *
     * @deprecated lease use {@link #readAll()}
     */
    @Deprecated
    public void read() {
        readAll();
    }

    /***
     * Parse all sheet content by default
     */
    public void readAll() {
        excelAnalyser.analysis(null, Boolean.TRUE);
    }

    /**
     * Parse the specified sheet，SheetNo start from 0
     *
     * @param readSheet
     *            Read sheet
     */
    public ExcelReader read(ReadSheet... readSheet) {
        return read(Arrays.asList(readSheet));
    }

    /**
     * Read multiple sheets.
     *
     * @param readSheetList
     * @return
     */
    public ExcelReader read(List<ReadSheet> readSheetList) {
        excelAnalyser.analysis(readSheetList, Boolean.FALSE);
        return this;
    }

    /**
     * Parse the specified sheet，SheetNo start from 1
     *
     * @param sheet
     *            Read sheet
     * @deprecated please us {@link #read(ReadSheet...)}
     */
    @Deprecated
    public void read(Sheet sheet) {
        ReadSheet readSheet = null;
        if (sheet != null) {
            readSheet = new ReadSheet();
            readSheet.setSheetNo(sheet.getSheetNo() - 1);
            readSheet.setSheetName(sheet.getSheetName());
            readSheet.setClazz(sheet.getClazz());
            readSheet.setHead(sheet.getHead());
            readSheet.setHeadRowNumber(sheet.getHeadLineMun());
        }
        read(readSheet);
    }

    /**
     * Parse the specified sheet
     *
     * @param sheet
     *            Read sheet
     * @param clazz
     *            object parsed into each row of value
     *
     * @deprecated Set the class in the sheet before read
     */
    @Deprecated
    public void read(Sheet sheet, Class clazz) {
        if (sheet != null) {
            sheet.setClazz(clazz);
        }
        read(sheet);
    }

    /**
     * Context for the entire execution process
     *
     * @return
     */
    public AnalysisContext analysisContext() {
        return excelAnalyser.analysisContext();
    }

    /**
     * Current executor
     *
     * @return
     */
    public ExcelReadExecutor excelExecutor() {
        return excelAnalyser.excelExecutor();
    }

    /**
     * Parse the workBook get all sheets
     *
     * @return workBook all sheets
     *
     * @deprecated please use {@link #excelExecutor()}
     */
    @Deprecated
    public List<Sheet> getSheets() {
        List<ReadSheet> sheetList = excelExecutor().sheetList();
        List<Sheet> sheets = new ArrayList<Sheet>();
        if (sheetList == null || sheetList.isEmpty()) {
            return sheets;
        }
        for (ReadSheet readSheet : sheetList) {
            Sheet sheet = new Sheet(readSheet.getSheetNo() + 1);
            sheet.setSheetName(readSheet.getSheetName());
            sheets.add(sheet);
        }
        return sheets;
    }

    /**
     *
     * @return
     * @deprecated please use {@link #analysisContext()}
     */
    @Deprecated
    public AnalysisContext getAnalysisContext() {
        return analysisContext();
    }

    /**
     * Complete the entire read file.Release the cache and close stream.
     */
    public void finish() {
        if (excelAnalyser != null) {
            excelAnalyser.finish();
        }
    }

    /**
     * Prevents calls to {@link #finish} from freeing the cache
     *
     */
    @Override
    protected void finalize() {
        try {
            finish();
        } catch (Throwable e) {
            LOGGER.warn("Destroy object failed", e);
        }
    }

}
