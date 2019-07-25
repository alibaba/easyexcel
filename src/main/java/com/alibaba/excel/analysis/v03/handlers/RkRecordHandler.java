package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class RkRecordHandler extends AbstractXlsRecordHandler {
    @Override
    public boolean support(Record record) {
        return RKRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        RKRecord rkrec = (RKRecord)record;

        this.row = rkrec.getRow();
        this.row = rkrec.getColumn();
        this.value = "";
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
