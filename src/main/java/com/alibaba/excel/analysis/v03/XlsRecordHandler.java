package com.alibaba.excel.analysis.v03;

import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.context.xls.XlsReadContext;

/**
 * Intercepts handle xls reads.
 *
 * @author Dan Zheng
 */
public interface XlsRecordHandler {
    /**
     * Whether to support
     *
     * @param xlsReadContext
     * @param record
     * @return
     */
    boolean support(XlsReadContext xlsReadContext, Record record);

    /**
     * Processing record
     *
     * @param xlsReadContext
     * @param record
     */
    void processRecord(XlsReadContext xlsReadContext, Record record);
}
