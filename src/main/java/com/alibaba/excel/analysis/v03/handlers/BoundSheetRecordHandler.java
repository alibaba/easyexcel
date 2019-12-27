package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BoundSheetRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BoundSheetRecord bsr = (BoundSheetRecord)record;
        xlsReadContext.boundSheetRecordList().add((BoundSheetRecord)record);
    }
}
