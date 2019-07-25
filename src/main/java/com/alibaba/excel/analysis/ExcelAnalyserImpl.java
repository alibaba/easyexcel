package com.alibaba.excel.analysis;

import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.AnalysisContextImpl;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {

    private AnalysisContext analysisContext;

    private ExcelExecutor excelExecutor;

    public ExcelAnalyserImpl(ReadWorkbook readWorkbook) {
        analysisContext = new AnalysisContextImpl(readWorkbook);
        choiceExcelExecutor();
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
        analysisContext.currentSheet(excelExecutor, readSheet);
        excelExecutor.execute();
        analysisContext.readSheetHolder().notifyAfterAllAnalysed(analysisContext);
    }

    @Override
    public void finish() {}

    @Override
    public com.alibaba.excel.analysis.ExcelExecutor excelExecutor() {
        return excelExecutor;
    }

    @Override
    public AnalysisContext analysisContext() {
        return analysisContext;
    }
}
