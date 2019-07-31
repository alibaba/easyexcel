package com.alibaba.excel.analysis.v03;

import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.metadata.CellData;

/**
 * Intercepts handle xls reads.
 *
 * @author Dan Zheng
 */
public interface XlsRecordHandler extends Comparable<XlsRecordHandler> {
    /**
     * Which tags are supported
     *
     * @param record
     * @return
     */
    boolean support(Record record);

    /**
     * Initialize
     */
    void init();

    /**
     * Processing record
     *
     * @param record
     */
    void processRecord(Record record);

    /**
     * Get row
     *
     * @return
     */
    int getRow();

    /**
     * Get column
     *
     * @return
     */
    int getColumn();

    /**
     * Get value
     *
     * @return
     */
    CellData getCellData();

    /**
     * Get order
     *
     * @return
     */
    int getOrder();
}
