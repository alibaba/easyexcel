package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.Record;

public class NoteRecordHandler extends AbstractXlsRecordHandler {
    @Override
    public boolean support(Record record) {
        return NoteRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        NoteRecord nrec = (NoteRecord)record;

        this.row = nrec.getRow();
        this.column = nrec.getColumn();
        // TODO: Find object to match nrec.getShapeId()
        this.value = "(TODO)";
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
