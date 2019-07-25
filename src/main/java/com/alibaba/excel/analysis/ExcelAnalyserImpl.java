package com.alibaba.excel.analysis;

import java.io.IOException;

import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.AnalysisContextImpl;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.read.metadata.holder.ReadWorkbookHolder;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {

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

    private void choiceExcelExecutor() {
        try {
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
        } catch (Exception e) {
            throw new ExcelAnalysisException("File type error，io must be available markSupported,you can do like "
                + "this <code> new BufferedInputStream(new FileInputStream(\\\"/xxxx\\\"))</code> \"", e);
        }
    }

    @Override
    public void analysis(ReadSheet readSheet) {
        try {
            analysisContext.currentSheet(excelExecutor, readSheet);
            excelExecutor.execute();
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
            readWorkbookHolder.getReadCache().destroy();
            if (analysisContext.readWorkbookHolder().getAutoCloseStream()
                && readWorkbookHolder.getInputStream() != null) {
                readWorkbookHolder.getInputStream().close();
            }
            if (readWorkbookHolder.getTempFile() != null) {
                FileUtils.delete(readWorkbookHolder.getTempFile());
            }
        } catch (IOException e) {
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
