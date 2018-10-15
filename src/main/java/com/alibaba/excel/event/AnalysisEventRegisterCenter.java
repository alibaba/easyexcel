package com.alibaba.excel.event;


/**
 *
 * @author jipengfei
 */
public interface AnalysisEventRegisterCenter {

    /**
     * @param name
     * @param listener
     */
    void appendLister(String name, AnalysisEventListener listener);


    /**
     * @param event
     */
    void notifyListeners(OneRowAnalysisFinishEvent event);

    /**
     */
    void cleanAllListeners();
}
