package com.alibaba.excel;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.ExcelAnalyser;
import com.alibaba.excel.analysis.ExcelAnalyserImpl;
import com.alibaba.excel.analysis.ExcelExecutor;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.parameter.AnalysisParam;
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

    private boolean finished = false;

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
     */
    public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener) {
        this(in, customContent, eventListener, null, true);
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
     */
    public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener,
        List<Converter> converters) {
        this(in, customContent, eventListener, converters, true);
    }

    /**
     * Create new reader
     *
     * @param param
     *            old param Deprecated
     * @param eventListener
     *            Callback method after each row is parsed.
     */
    @Deprecated
    public ExcelReader(AnalysisParam param, AnalysisEventListener eventListener) {
        this(param.getIn(), param.getExcelTypeEnum(), param.getCustomContent(), eventListener, true);
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
     */
    @Deprecated
    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
        AnalysisEventListener eventListener, boolean trim) {
        this(in, excelTypeEnum, customContent, eventListener, null, trim);
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
     */
    public ExcelReader(InputStream in, Object customContent, AnalysisEventListener eventListener,
        List<Converter> converters, boolean trim) {
        this(in, ExcelTypeEnum.valueOf(in), customContent, eventListener, converters, trim);
    }

    public ExcelReader(InputStream in, Object excelTypeEnum, AnalysisEventListener<Object> eventListener,
        boolean trim) {
        this(in, ExcelTypeEnum.valueOf(in), null, eventListener, null, trim);
    }

    public ExcelReader(InputStream in, ExcelTypeEnum excelTypeEnum, Object customContent,
        AnalysisEventListener eventListener, List<Converter> converters, boolean trim) {
        validateParam(in, eventListener);
        analyser = new ExcelAnalyserImpl(in, excelTypeEnum, customContent, eventListener, trim);
        initConverters(analyser, converters);
    }

    private void initConverters(ExcelAnalyser analyser, List<Converter> converters) {
        if (converters != null && converters.size() > 0) {
            for (Converter c : converters) {
                analyser.getAnalysisContext().getConverterRegistryCenter().register(c);
            }
        }
    }

    /**
     * Parse all sheet content by default
     */
    public void read() {
        ExcelExecutor excelExecutor = excelAnalyser.excelExecutor();
        if (excelExecutor.sheetList().isEmpty()) {
            LOGGER.warn("Excel doesn't have any sheets.");
            return;
        }
        for (Sheet sheet : excelExecutor.sheetList()) {
            read(sheet);
        }
    }

    /**
     * Parse the specified sheet，SheetNo start from 1
     *
     * @param sheet
     *            Read sheet
     */
    public void read(Sheet sheet) {
        checkFinished();
        excelAnalyser.analysis(sheet);
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
        checkFinished();
        return excelAnalyser.analysisContext();
    }

    /**
     * Current executor
     * 
     * @return
     */
    public ExcelExecutor excelExecutor() {
        checkFinished();
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
        return excelExecutor().sheetList();
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
        if (finished) {
            return;
        }
        finished = true;
        excelAnalyser.finish();
    }

    /**
     * Prevents calls to {@link #finish} from freeing the cache
     * 
     * @throws Throwable
     */
    @Override
    protected void finalize() {
        if (finished) {
            return;
        }
        try {
            excelAnalyser.finish();
        } catch (Exception e) {
            LOGGER.warn("Destroy object failed", e);
        }
    }

    private void checkFinished() {
        if (finished) {
            throw new ExcelAnalysisException("Can not use a finished reader.");
        }
    }
}
