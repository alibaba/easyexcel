package com.alibaba.excel.read.event;


/**
 * 管理每个监听者
 *
 * @author jipengfei
 */
public interface AnalysisEventRegisterCenter {

    /**
     * 增加监听者
     * @param name 名称
     * @param listener 监听器
     */
    void appendLister(String name, AnalysisEventListener listener);


    /**
     * 通知所有监听者
     * @param event 事件
     */
    void notifyListeners(OneRowAnalysisFinishEvent event);

    /**
     * 清空所有监听者
     */
    void cleanAllListeners();
}
