package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.ObjRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.TextObjectRecord;

import com.alibaba.excel.analysis.v03.AbstractXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;

/**
 * Record handler
 *
 * @author Jiaju Zhuang
 */
public class TextObjectRecordHandler extends AbstractXlsRecordHandler {
    public TextObjectRecordHandler(XlsReadContext analysisContext) {
        super(analysisContext);
    }

    @Override
    public boolean support(Record record) {
        return TextObjectRecord.sid == record.getSid() || ObjRecord.sid == record.getSid();
    }

    @Override
    public void processRecord(Record record) {
        System.out.println(record);
    }

    @Override
    public void init() {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
