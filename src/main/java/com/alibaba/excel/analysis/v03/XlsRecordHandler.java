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
     *            Excel analysis record
     * @return Which tags are supported
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
     * @return Row index
     */
    int getRow();

    /**
     * Get column
     *
     * @return Column index
     */
    int getColumn();

    /**
     * Get value
     *
     * @return Excel internal cell data
     */
    CellData getCellData();

    /**
     * Get order
     *
     * @return Order
     */
    int getOrder();
}
