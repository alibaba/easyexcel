package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.record.Record;

public class MissingCellDummyRecordHandler extends AbstractXlsRecordHandler {
    @Override
    public boolean support(Record record) {
        return record instanceof MissingCellDummyRecord;
    }

    @Override
    public void init() {

    }

    @Override
    public void processRecord(Record record) {
        MissingCellDummyRecord mcdr = (MissingCellDummyRecord)record;
        this.row = mcdr.getRow();
        this.column = mcdr.getColumn();
        this.value = "";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
