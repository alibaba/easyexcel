package com.alibaba.excel.event;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;

/**
 * Receives the return of each piece of data parsed
 *
 * @author jipengfei
 */
public abstract class AnalysisEventListener<T> implements ReadListener<T> {

    /**
     * All listeners receive this method when any one Listener does an error report. If an exception is thrown here, the
     * entire read will terminate.
     *
     * @param exception
     * @param context
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        throw exception;
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }
}
