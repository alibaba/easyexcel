package com.alibaba.excel;

import java.io.Closeable;
import java.util.Arrays;
import java.util.List;

import com.alibaba.excel.analysis.ExcelAnalyser;
import com.alibaba.excel.analysis.ExcelAnalyserImpl;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;

/**
 * Excel readers are all read in event mode.
 *
 * @author jipengfei
 */
public class ExcelReader implements Closeable {

    /**
     * Analyser
     */
    private final ExcelAnalyser excelAnalyser;

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
     * @param readSheet Read sheet
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

    @Override
    public void close() {
        finish();
    }
}
