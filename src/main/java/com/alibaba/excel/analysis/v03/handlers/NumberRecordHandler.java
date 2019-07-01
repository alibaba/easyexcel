package com.alibaba.excel.analysis.v03.handlers;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;

public class NumberRecordHandler extends AbstractXlsRecordHandler {
    private FormatTrackingHSSFListener formatListener;
    public NumberRecordHandler(FormatTrackingHSSFListener formatListener) {
        this.formatListener = formatListener;
    }
    @Override
    public boolean support(Record record) {
        return NumberRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        NumberRecord numrec = (NumberRecord)record;
        this.row = numrec.getRow();
        this.column = numrec.getColumn();
        // Format
        this.value = formatListener.formatNumberDateCell(numrec);
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
