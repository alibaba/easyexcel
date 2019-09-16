package com.alibaba.excel.analysis.v03.handlers;

import java.math.BigDecimal;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
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
        this.cellData = new CellData(BigDecimal.valueOf(numrec.getValue()));
        this.cellData.setDataFormat(formatListener.getFormatIndex(numrec));
        this.cellData.setDataFormatString(formatListener.getFormatString(numrec));
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
