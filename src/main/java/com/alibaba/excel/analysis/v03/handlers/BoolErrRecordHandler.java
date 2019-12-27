package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BoolErrRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BoolErrRecord ber = (BoolErrRecord)record;
        xlsReadContext.cellMap().put((int)ber.getColumn(),
            CellData.newInstance(ber.getBooleanValue(), ber.getRow(), (int)ber.getColumn()));
        xlsReadContext.tempRowType(RowTypeEnum.DATA);
    }
}
