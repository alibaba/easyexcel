package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.XlsRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;

/**
 * Abstract xls record handler
 *
 * @author Jiaju Zhuang
 **/
public abstract class AbstractXlsRecordHandler implements XlsRecordHandler {

    @Override
    public boolean support(XlsReadContext xlsReadContext, Record record) {
        return true;
    }
}
