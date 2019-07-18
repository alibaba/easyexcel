package com.alibaba.excel.analysis;

import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.AnalysisContextImpl;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Workbook;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {

    private AnalysisContext analysisContext;

    private ExcelExecutor excelExecutor;

    public ExcelAnalyserImpl(Workbook workbook) {
        analysisContext = new AnalysisContextImpl(workbook);
        choiceExcelExecutor();
    }

    private void choiceExcelExecutor() {
        try {
            ExcelTypeEnum excelType = analysisContext.currentWorkbookHolder().getExcelType();
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
    public void analysis(Sheet sheet) {
        analysisContext.currentSheet(sheet);
        excelExecutor.execute();
        analysisContext.getEventListener().doAfterAllAnalysed(analysisContext);
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
