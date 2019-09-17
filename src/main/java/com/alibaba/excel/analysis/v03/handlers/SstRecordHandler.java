package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class SstRecordHandler extends AbstractXlsRecordHandler {
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
                this.cellData = new CellData(CellDataTypeEnum.EMPTY);
            } else {
                this.cellData = new CellData(sstRecord.getString(lsrec.getSSTIndex()).toString());
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
