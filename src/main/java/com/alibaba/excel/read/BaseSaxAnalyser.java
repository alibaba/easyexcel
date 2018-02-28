package com.alibaba.excel.read;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.read.event.AnalysisEventRegisterCenter;
import com.alibaba.excel.read.event.OneRowAnalysisFinishEvent;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * 抽象sax模式 excel 解析类
 *
 * @author jipengfei
 */
public abstract class BaseSaxAnalyser implements AnalysisEventRegisterCenter, ExcelAnalyser {

    protected AnalysisContext analysisContext;

    private LinkedHashMap<String, AnalysisEventListener> listeners = new LinkedHashMap<String, AnalysisEventListener>();

    /**
     * 开始执行解析
     */
    protected abstract void execute();

    public void init(InputStream inputStream, ExcelTypeEnum excelTypeEnum, Object custom,
                     AnalysisEventListener eventListener, boolean trim) {
    }

    public void appendLister(String name, AnalysisEventListener listener) {
        if (!listeners.containsKey(name)) {
            listeners.put(name, listener);
        }
    }

    public void analysis(Sheet sheetParam) {
        execute();
    }

    public void analysis() {
        execute();
    }

    /**
     * 清空所有监听者
     */
    public void cleanAllListeners() {
        listeners = new LinkedHashMap<String, AnalysisEventListener>();
    }

    public void notifyListeners(OneRowAnalysisFinishEvent event) {
        analysisContext.setCurrentRowAnalysisResult(event.getData());

        //表头数据
        if (analysisContext.getCurrentRowNum() < analysisContext.getCurrentSheet().getHeadLineMun()) {
            if (analysisContext.getCurrentRowNum() <= analysisContext.getCurrentSheet().getHeadLineMun() - 1) {
                analysisContext.buildExcelHeadProperty(null,
                    (List<String>)analysisContext.getCurrentRowAnalysisResult());
            }
        } else {
            analysisContext.setCurrentRowAnalysisResult(event.getData());
            for (Map.Entry<String, AnalysisEventListener> entry : listeners.entrySet()) {
                entry.getValue().invoke(analysisContext.getCurrentRowAnalysisResult(), analysisContext);
            }
        }
    }
}
