package com.alibaba.excel.read;

import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.context.AnalysisContextImpl;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.modelbuild.ModelBuildEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {

    private AnalysisContext analysisContext;

    private BaseSaxAnalyser saxAnalyser;

    private BaseSaxAnalyser getSaxAnalyser() {
        if (saxAnalyser == null) {
            if (ExcelTypeEnum.XLS.equals(analysisContext.getExcelType())) {
                this.saxAnalyser = new SaxAnalyserV03(analysisContext);
            } else {
                try {
                    this.saxAnalyser = new SaxAnalyserV07(analysisContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this.saxAnalyser;
    }

    public void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom,
                     AnalysisEventListener eventListener, boolean trim) {
        analysisContext = new AnalysisContextImpl(inputStream, excelTypeEnum, custom,
            eventListener, trim);
    }

    public void analysis(Sheet sheetParam) {
        analysisContext.setCurrentSheet(sheetParam);
        analysis();
    }

    public void analysis() {
        BaseSaxAnalyser saxAnalyser = getSaxAnalyser();
        appendListeners(saxAnalyser);
        saxAnalyser.execute();

        analysisContext.getEventListener().doAfterAllAnalysed(analysisContext);
    }

    public List<Sheet> getSheets() {
        BaseSaxAnalyser saxAnalyser = getSaxAnalyser();
        saxAnalyser.cleanAllListeners();
        return saxAnalyser.getSheets();
    }

    public void stop() {
        saxAnalyser.stop();
    }

    private void appendListeners(BaseSaxAnalyser saxAnalyser) {
        if (analysisContext.getCurrentSheet() != null && analysisContext.getCurrentSheet().getClazz() != null) {
            saxAnalyser.appendLister("model_build_listener", new ModelBuildEventListener());
        }
        if (analysisContext.getEventListener() != null) {
            saxAnalyser.appendLister("user_define_listener", analysisContext.getEventListener());
        }
    }

    protected void finalize() throws Throwable {
        stop();
    }

}
