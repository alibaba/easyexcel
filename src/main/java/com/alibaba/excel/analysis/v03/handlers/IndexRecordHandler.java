package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.IndexRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;

/**
 * Record handler
 *
 * @author Jiaju Zhuang
 */
public class IndexRecordHandler extends AbstractXlsRecordHandler {

    public IndexRecordHandler(XlsReadContext analysisContext) {
        super(analysisContext);
    }

    @Override
    public boolean support(Record record) {
        return record instanceof IndexRecord;
    }

    @Override
    public void init() {}

    @Override
    public void processRecord(Record record) {
        if (analysisContext.readSheetHolder() == null) {
            return;
        }
        analysisContext.readSheetHolder().setApproximateTotalRowNumber(((IndexRecord)record).getLastRowAdd1());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
