package com.alibaba.excel.analysis;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.BooleanConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.converters.ConverterRegistryCenter;
import com.alibaba.excel.converters.DateConverter;
import com.alibaba.excel.converters.Double2Converter;
import com.alibaba.excel.converters.DoubleConverter;
import com.alibaba.excel.converters.FloatConverter;
import com.alibaba.excel.converters.IntegerConverter;
import com.alibaba.excel.converters.LongConverter;
import com.alibaba.excel.converters.StringConverter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.AnalysisEventRegistryCenter;
import com.alibaba.excel.event.AnalysisFinishEvent;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.ExcelHeadProperty;
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
        converters.put(converter.getName(), converter);
    }

    @Override
    public void beforeAnalysis() {
        registerDefaultConverters();
    }

    private void registerDefaultConverters() {
        Double2Converter double2Converter = new Double2Converter();
        converters.put(ConverterKey.buildConverterKey(double2Converter.supportJavaTypeKey(),
            double2Converter.supportExcelTypeKey()), double2Converter);

        StringConverter s = new StringConverter();
        converters.put(s.getName(), s);
        DateConverter d = new DateConverter(this.analysisContext);
        converters.put(d.getName(), d);
        IntegerConverter i = new IntegerConverter();
        converters.put(i.getName(), i);
        DoubleConverter dc = new DoubleConverter();
        converters.put(dc.getName(), dc);
        LongConverter l = new LongConverter();
        converters.put(l.getName(), l);
        FloatConverter f = new FloatConverter();
        converters.put(f.getName(), f);
        BooleanConverter b = new BooleanConverter();
        converters.put(b.getName(), b);
    }

    @Override
    public Collection<Converter> getConverters() {
        return converters.values();
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

    private void buildExcelHeadProperty(Class<? extends BaseRowModel> clazz, List<String> headOneRow) {
        ExcelHeadProperty excelHeadProperty =
            ExcelHeadProperty.buildExcelHeadProperty(this.analysisContext.getExcelHeadProperty(), clazz, headOneRow);
        this.analysisContext.setExcelHeadProperty(excelHeadProperty);
    }
}
