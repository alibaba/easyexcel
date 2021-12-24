package com.github.byteautumn.excel.analysis;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.excel.analysis.ExcelAnalyserImpl;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;

import lombok.extern.slf4j.Slf4j;

/**
 * @author byte.autumn
 */
@Slf4j
public class ExcelAnalyserImpl2 extends ExcelAnalyserImpl {
    public ExcelAnalyserImpl2(ReadWorkbook readWorkbook) {
        super(readWorkbook);
    }

    @Override
    public void analysis(List<ReadSheet> readSheetList, Boolean readAll) {
        AnalysisContext analysisContext = super.analysisContext();
        ExcelReadExecutor excelReadExecutor = super.excelExecutor();
        try {
            if (!readAll && CollectionUtils.isEmpty(readSheetList)) {
                throw new IllegalArgumentException("Specify at least one read sheet.");
            }
            analysisContext.readWorkbookHolder().setParameterSheetDataList(readSheetList);
            analysisContext.readWorkbookHolder().setReadAll(readAll);
            try {
                excelReadExecutor.execute();
            } catch (ExcelAnalysisStopException e) {
                if (log.isDebugEnabled()) {
                    log.debug("Custom stop!");
                }
            }
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }


}
