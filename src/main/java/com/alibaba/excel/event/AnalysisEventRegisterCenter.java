package com.alibaba.excel.event;


/**
 *
 * @author jipengfei
 */
public interface AnalysisEventRegisterCenter {

    /**
     * @param name 监听名定义
     * @param listener 具体实现
     */
    void appendLister(String name, AnalysisEventListener listener);


    /**
     * @param event 事件
     */
    void notifyListeners(OneRowAnalysisFinishEvent event);

    /**
     */
    void cleanAllListeners();
}
