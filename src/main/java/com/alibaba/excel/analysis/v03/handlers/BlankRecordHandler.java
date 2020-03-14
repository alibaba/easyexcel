package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BlankRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BlankRecord br = (BlankRecord)record;
        xlsReadContext.xlsReadSheetHolder().getCellMap().put((int)br.getColumn(),
            CellData.newEmptyInstance(br.getRow(), (int)br.getColumn()));
    }
}
