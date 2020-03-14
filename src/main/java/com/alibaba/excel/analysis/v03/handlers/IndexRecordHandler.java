package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.IndexRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;

/**
 * Record handler
 *
 * @author Jiaju Zhuang
 */
public class IndexRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {
    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        if (xlsReadContext.readSheetHolder() == null) {
            return;
        }
        xlsReadContext.readSheetHolder().setApproximateTotalRowNumber(((IndexRecord)record).getLastRowAdd1());
    }
}
