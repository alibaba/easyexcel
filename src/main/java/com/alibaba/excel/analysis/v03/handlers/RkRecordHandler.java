package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class RkRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        RKRecord re = (RKRecord)record;
        xlsReadContext.cellMap().put((int)re.getColumn(), CellData.newEmptyInstance(re.getRow(), (int)re.getColumn()));
    }
}
