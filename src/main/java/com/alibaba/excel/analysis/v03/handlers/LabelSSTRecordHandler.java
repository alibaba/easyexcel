package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.CellData;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class LabelSSTRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        LabelSSTRecord lsrec = (LabelSSTRecord)record;
        ReadCache readCache = xlsReadContext.readWorkbookHolder().getReadCache();
        if (readCache == null) {
            xlsReadContext.cellMap().put((int)lsrec.getColumn(),
                CellData.newEmptyInstance(lsrec.getRow(), (int)lsrec.getColumn()));
            return;
        }
        String data = readCache.get(lsrec.getSSTIndex());
        if (data == null) {
            xlsReadContext.cellMap().put((int)lsrec.getColumn(),
                CellData.newEmptyInstance(lsrec.getRow(), (int)lsrec.getColumn()));
            return;
        }
        xlsReadContext.cellMap().put((int)lsrec.getColumn(),
            CellData.newInstance(data, lsrec.getRow(), (int)lsrec.getColumn()));
        xlsReadContext.tempRowType(RowTypeEnum.DATA);
    }
}
