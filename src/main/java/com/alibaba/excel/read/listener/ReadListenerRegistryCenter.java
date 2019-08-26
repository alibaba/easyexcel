package com.alibaba.excel.read.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.event.AnalysisFinishEvent;

/**
 * Registry center.
 *
 * @author jipengfei
 */
public interface ReadListenerRegistryCenter {

    /**
     * register
     *
     * @param listener
     *            Analysis listener
     */
    void register(AnalysisEventListener listener);

    /**
     * Parse one row to notify all event listeners
     *
     * @param event
     *            parse event
     * @param analysisContext
     *            Analysis context
     */
    void notifyEndOneRow(AnalysisFinishEvent event, AnalysisContext analysisContext);

    /**
     * Notify after all analysed
     *
     * @param analysisContext
     *            Analysis context
     */
    void notifyAfterAllAnalysed(AnalysisContext analysisContext);
}
