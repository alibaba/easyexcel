package com.alibaba.excel.event;

import com.alibaba.excel.context.AnalysisContext;

/**
 * Interface to listen for read results
 * 
 * @author zhuangjiaju
 */
public interface ReadListener<T> extends Listener {
    /**
     * All listeners receive this method when any one Listener does an error report. If an exception is thrown here, the
     * * entire read will terminate.
     * 
     * @param exception
     * @param context
     * @throws Exception
     */
    void onException(Exception exception, AnalysisContext context) throws Exception;

    /**
     * when analysis one row trigger invoke function
     *
     * @param object
     *            one row value
     * @param context
     *            analysis context
     */
    void invoke(T object, AnalysisContext context);

    /**
     * if have something to do after all analysis
     *
     * @param context
     */
    void doAfterAllAnalysed(AnalysisContext context);
}
