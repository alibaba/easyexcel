package com.alibaba.excel.analysis.v03;

import org.apache.poi.hssf.record.Record;

public interface XlsRecordHandler extends Comparable<XlsRecordHandler> {
    boolean support(Record record);
    void init();
    void processRecord(Record record);
    int getRow();
    int getColumn();
    String getValue();
    int getOrder();
}
