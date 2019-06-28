package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.Record;

public class LabelRecordHandler extends AbstractXlsRecordHandler {
    @Override
    public boolean support(Record record) {
        return LabelRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        LabelRecord lrec = (LabelRecord)record;
        this.row = lrec.getRow();
        this.column = lrec.getColumn();
        this.value = lrec.getValue();
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
