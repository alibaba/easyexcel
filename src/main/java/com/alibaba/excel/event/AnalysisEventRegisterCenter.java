package com.alibaba.excel.event;


/**
 * 管理每个监听者
 *
 * @author jipengfei
 */
public interface AnalysisEventRegisterCenter {

    /**
     * 增加监听者
     * @param name
     * @param listener
     */
    void appendLister(String name, AnalysisEventListener listener);


    /**
     * 通知所有监听者
     * @param event
     */
    void notifyListeners(OneRowAnalysisFinishEvent event);

    /**
     * 清空所有监听者
     */
    void cleanAllListeners();
}
