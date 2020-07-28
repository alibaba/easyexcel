package com.alibaba.excel.read.listener;

import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.Listener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;

/**
 * Interface to listen for read results
 *
 * @author Jiaju Zhuang
 */
public interface ReadListener<T> extends Listener {
    /**
     * All listeners receive this method when any one Listener does an error report. If an exception is thrown here, the
     * entire read will terminate.
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    void onException(Exception exception, AnalysisContext context) throws Exception;

    /**
     * When analysis one head row trigger invoke function.
     *
     * @param headMap
     * @param context
     */
    void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context);

    /**
     * When analysis one row trigger invoke function.
     *
     * @param data
     *            one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     *            analysis context
     */
    void invoke(T data, AnalysisContext context);

    /**
     * The current method is called when extra information is returned
     *
     * @param extra
     *            extra information
     * @param context
     *            analysis context
     */
    void extra(CellExtra extra, AnalysisContext context);

    /**
     * if have something to do after all analysis
     *
     * @param context
     */
    void doAfterAllAnalysed(AnalysisContext context);

    /**
     * Verify that there is another piece of data.You can stop the read by returning false
     *
     * @param context
     * @return
     */
    boolean hasNext(AnalysisContext context);
}
