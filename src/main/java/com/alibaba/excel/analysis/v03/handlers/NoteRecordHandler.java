package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class NoteRecordHandler extends AbstractXlsRecordHandler {
    public NoteRecordHandler(XlsReadContext analysisContext) {
        super(analysisContext);
    }

    @Override
    public boolean support(Record record) {
        return NoteRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        NoteRecord nrec = (NoteRecord)record;
        this.row = nrec.getRow();
        this.column = nrec.getColumn();
        this.cellData = new CellData(CellDataTypeEnum.EMPTY);
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
