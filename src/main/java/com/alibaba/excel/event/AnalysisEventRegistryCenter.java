package com.alibaba.excel.event;

/**
 * Event center.
 *
 * @author jipengfei
 */
public interface AnalysisEventRegistryCenter {

    /**
     * Append listener
     *
     * @param name     listener name.
     * @param listener Callback method after each row is parsed.
     */
    void register(String name, AnalysisEventListener<Object> listener);

    /**
     * Parse one row to notify all event listeners
     *
     * @param event parse event
     */
    void notify(AnalysisFinishEvent event);

    /**
     * Clean all listeners.
     */
    void cleanAllListeners();
    
    /**
     * clean listener by name
     * @param name the listener name
     */
    void cleanListener(String name);
}
