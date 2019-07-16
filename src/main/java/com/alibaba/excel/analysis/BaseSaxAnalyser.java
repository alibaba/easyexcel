package com.alibaba.excel.analysis;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.converters.DefaultConverterLoader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.AnalysisEventRegistryCenter;
import com.alibaba.excel.event.AnalysisFinishEvent;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;

/**
 * @author jipengfei
 */
public abstract class BaseSaxAnalyser implements ConverterRegistryCenter, AnalysisEventRegistryCenter, ExcelAnalyser {

    protected AnalysisContext analysisContext;

    private LinkedHashMap<String, AnalysisEventListener<Object>> listeners =
        new LinkedHashMap<String, AnalysisEventListener<Object>>();

    private Map<ConverterKey, Converter> converters = new HashMap<ConverterKey, Converter>();

    /**
     * execute method
     */
    protected abstract void execute();

    @Override
    public void register(String name, AnalysisEventListener<Object> listener) {
        if (!listeners.containsKey(name)) {
            listeners.put(name, listener);
        }
    }

    @Override
    public void register(Converter converter) {
        converters.put(new ConverterKey(converter.supportJavaTypeKey(),converter.supportExcelTypeKey()), converter);
    }

    @Override
    public void beforeAnalysis() {
        registerDefaultConverters();
    }

    private void registerDefaultConverters() {
        converters.putAll(DefaultConverterLoader.loadDefaultReadConverter());
    }

    @Override
    public Map<ConverterKey, Converter> getConverters() {
        return converters;
    }

    @Override
    public void analysis(Sheet sheetParam) {
        analysisContext.setCurrentSheet(sheetParam);
        execute();
    }

    @Override
    public void analysis() {
        execute();
    }

    @Override
    public AnalysisContext getAnalysisContext() {
        return analysisContext;
    }

    /**
     */
    @Override
    public void cleanAllListeners() {
        listeners.clear();
    }

    @Override
    public void cleanListener(String name) {
        listeners.remove(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void notify(AnalysisFinishEvent event) {
        analysisContext.setCurrentRowAnalysisResult(event.getAnalysisResult());
        /** Parsing header content **/
        if (analysisContext.getCurrentRowNum() < analysisContext.getCurrentSheet().getReadHeadRowNumber()) {
            if (analysisContext.getCurrentRowNum() <= analysisContext.getCurrentSheet().getReadHeadRowNumber() - 1) {
                buildExcelHeadProperty(null, (List<String>)analysisContext.getCurrentRowAnalysisResult());
            }
        } else {
            for (Entry<String, AnalysisEventListener<Object>> entry : listeners.entrySet()) {
                entry.getValue().invoke(analysisContext.getCurrentRowAnalysisResult(), analysisContext);
            }
        }
    }

    private void buildExcelHeadProperty(Class clazz, List<String> headOneRow) {
        ExcelHeadProperty excelHeadProperty =
            ExcelHeadProperty.buildExcelHeadProperty(this.analysisContext.getExcelHeadProperty(), clazz, headOneRow);
        this.analysisContext.setExcelHeadProperty(excelHeadProperty);
    }
}
