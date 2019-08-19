package com.alibaba.excel.analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.AnalysisContextImpl;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAnalyserImpl.class);

    private AnalysisContext analysisContext;

    private ExcelExecutor excelExecutor;

    public ExcelAnalyserImpl(ReadWorkbook readWorkbook) {
        try {
            analysisContext = new AnalysisContextImpl(readWorkbook);
            choiceExcelExecutor();
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }

    private void choiceExcelExecutor() throws Exception {
        ExcelTypeEnum excelType = analysisContext.readWorkbookHolder().getExcelType();
        if (excelType == null) {
            excelExecutor = new XlsxSaxAnalyser(analysisContext);
            return;
        }
        switch (excelType) {
            case XLS:
                excelExecutor = new XlsSaxAnalyser(analysisContext);
                break;
            case XLSX:
                excelExecutor = new XlsxSaxAnalyser(analysisContext);
                break;
            default:
        }
    }

    @Override
    public void analysis(ReadSheet readSheet) {
        try {
            analysisContext.currentSheet(excelExecutor, readSheet);
            try {
                excelExecutor.execute();
            } catch (ExcelAnalysisStopException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Custom stop!");
                }
            }
            analysisContext.readSheetHolder().notifyAfterAllAnalysed(analysisContext);
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }

    @Override
    public void finish() {
        if (analysisContext == null || analysisContext.readWorkbookHolder() == null) {
            return;
        }
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();
        try {
            if (readWorkbookHolder.getReadCache() != null) {
                readWorkbookHolder.getReadCache().destroy();
            }
        } catch (Throwable e) {
            throw new ExcelAnalysisException("Can not close IO", e);
        }
        try {
            if (analysisContext.readWorkbookHolder().getAutoCloseStream()
                && readWorkbookHolder.getInputStream() != null) {
                readWorkbookHolder.getInputStream().close();
            }
        } catch (Throwable e) {
            throw new ExcelAnalysisException("Can not close IO", e);
        }
        try {
            if (readWorkbookHolder.getTempFile() != null) {
                FileUtils.delete(readWorkbookHolder.getTempFile());
            }
        } catch (Throwable e) {
            throw new ExcelAnalysisException("Can not close IO", e);
        }
    }

    @Override
    public com.alibaba.excel.analysis.ExcelExecutor excelExecutor() {
        return excelExecutor;
    }

    @Override
    public AnalysisContext analysisContext() {
        return analysisContext;
    }
}
