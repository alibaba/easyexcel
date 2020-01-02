package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.CellExtra;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class NoteRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        NoteRecord nr = (NoteRecord)record;
        String note = xlsReadContext.xlsReadSheetHolder().getObjectCacheMap().get(nr.getShapeId());
        CellExtra cellExtra = new CellExtra();
        cellExtra.setRowIndex(nr.getRow());
        cellExtra.setRowIndex(nr.getColumn());
        cellExtra.setNote(note);
        xlsReadContext.xlsReadSheetHolder().getCellMap().put(nr.getColumn(), cellExtra);
        xlsReadContext.xlsReadSheetHolder().setTempRowType(RowTypeEnum.EXTRA);
    }
}
