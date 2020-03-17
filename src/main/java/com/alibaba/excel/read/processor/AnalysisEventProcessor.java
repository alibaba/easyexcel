package com.alibaba.excel.read.processor;

import com.alibaba.excel.context.AnalysisContext;

/**
 *
 * Event processor
 *
 * @author jipengfei
 */
public interface AnalysisEventProcessor {
    /**
     * Read extra information
     *
     * @param analysisContext
     */
    void extra(AnalysisContext analysisContext);

    /**
     * End row
     *
     * @param analysisContext
     */
    void endRow(AnalysisContext analysisContext);

    /**
     * Notify after all analysed
     *
     * @param analysisContext
     *            Analysis context
     */
    void endSheet(AnalysisContext analysisContext);
}
