package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.record.*;

public class SSTRecordHandler extends AbstractXlsRecordHandler {
    private SSTRecord sstRecord;
    @Override
    public boolean support(Record record) {
        return SSTRecord.sid == record.getSid() || LabelSSTRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        if (record.getSid() == SSTRecord.sid) {
            sstRecord = (SSTRecord)record;
        } else if (record.getSid() == LabelSSTRecord.sid) {
            LabelSSTRecord lsrec = (LabelSSTRecord)record;

            this.row = lsrec.getRow();
            this.column = lsrec.getColumn();
            if (sstRecord == null) {
                this.value = "";
            } else {
                this.value = sstRecord.getString(lsrec.getSSTIndex()).toString();
            }
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
