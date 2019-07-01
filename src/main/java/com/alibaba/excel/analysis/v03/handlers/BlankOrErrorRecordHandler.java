package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.record.*;

public class BlankOrErrorRecordHandler extends AbstractXlsRecordHandler {

    @Override
    public boolean support(Record record) {
        return BlankRecord.sid == record.getSid() || BoolErrRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        if (record.getSid() == BlankRecord.sid) {
            BlankRecord br = (BlankRecord)record;
            this.row = br.getRow();
            this.column = br.getColumn();
            this.value = "";
        } else if (record.getSid() == BoolErrRecord.sid) {
            BoolErrRecord ber = (BoolErrRecord)record;
            this.row = ber.getRow();
            this.column = ber.getColumn();
            this.value = "";
        }
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
