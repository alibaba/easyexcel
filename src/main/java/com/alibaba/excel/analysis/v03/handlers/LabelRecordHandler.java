package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
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
        this.cellData = new CellData(lrec.getValue());
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
