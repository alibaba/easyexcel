package com.alibaba.excel.analysis;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.analysis.v03.XlsSaxAnalyser;
import com.alibaba.excel.analysis.v07.XlsxSaxAnalyser;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.AnalysisContextImpl;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.event.ModelBuildEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {

    private AnalysisContext analysisContext;

    private BaseSaxAnalyser saxAnalyser;

    public ExcelAnalyserImpl(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom,
                             AnalysisEventListener<Object> eventListener, boolean trim) {
        ConverterRegistryCenter center = new ConverterRegistryCenter() {
            @Override
            public void register(Converter converter) {
                saxAnalyser.register(converter);
            }

            @Override
            public Map<ConverterKey, Converter> getConverters() {
                return saxAnalyser.getConverters();
            }
        };
        analysisContext = new AnalysisContextImpl(inputStream, excelTypeEnum, custom,
            eventListener, center, trim);
        this.saxAnalyser = getSaxAnalyser();
    }

    private BaseSaxAnalyser getSaxAnalyser() {
        if (saxAnalyser != null) {
            return this.saxAnalyser;
        }
        try {
            if (analysisContext.getExcelType() != null) {
                switch (analysisContext.getExcelType()) {
                    case XLS:
                        this.saxAnalyser = new XlsSaxAnalyser(analysisContext);
                        break;
                    case XLSX:
                        this.saxAnalyser = new XlsxSaxAnalyser(analysisContext);
                        break;
                }
            } else {
                try {
                    this.saxAnalyser = new XlsxSaxAnalyser(analysisContext);
                } catch (Exception e) {
                    if (!analysisContext.getInputStream().markSupported()) {
                        throw new ExcelAnalysisException(
                            "Xls must be available markSupported,you can do like this <code> new "
                                + "BufferedInputStream(new FileInputStream(\"/xxxx\"))</code> ");
                    }
                    this.saxAnalyser = new XlsSaxAnalyser(analysisContext);
                }
            }
        } catch (Exception e) {
            throw new ExcelAnalysisException("File type error，io must be available markSupported,you can do like "
                + "this <code> new BufferedInputStream(new FileInputStream(\\\"/xxxx\\\"))</code> \"", e);
        }

        return this.saxAnalyser;
    }
    
    @Override
    public void analysis(Sheet sheetParam) {
        analysisContext.setCurrentSheet(sheetParam);
        analysis();
    }
    @Override
    public AnalysisContext getAnalysisContext() {
        return analysisContext;
    }
    @Override
    public void analysis() {
        saxAnalyser.execute();
        analysisContext.getEventListener().doAfterAllAnalysed(analysisContext);
    }

    @Override
    public List<Sheet> getSheets() {
        return this.saxAnalyser.getSheets();
    }

    private void registerListeners(BaseSaxAnalyser saxAnalyser) {
        saxAnalyser.cleanAllListeners();
        if (analysisContext.getCurrentSheet() != null && analysisContext.getCurrentSheet().getClazz() != null) {
            saxAnalyser.register("model_build_listener", new ModelBuildEventListener(this.saxAnalyser.getConverters()));
        }
        if (analysisContext.getEventListener() != null) {
            saxAnalyser.register("user_define_listener", analysisContext.getEventListener());
        }
    }

    @Override
    public void beforeAnalysis() {
        BaseSaxAnalyser saxAnalyser = getSaxAnalyser();
        registerListeners(saxAnalyser);
    }
}
