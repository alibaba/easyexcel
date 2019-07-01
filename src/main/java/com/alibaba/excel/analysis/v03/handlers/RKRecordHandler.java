package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;

public class RKRecordHandler extends AbstractXlsRecordHandler {
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
