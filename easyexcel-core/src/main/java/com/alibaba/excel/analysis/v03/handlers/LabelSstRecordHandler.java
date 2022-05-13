package com.alibaba.excel.analysis.v03.handlers;

import java.util.Map;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.enums.RowTypeEnum;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.data.ReadCellData;

import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.Record;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class LabelSstRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        LabelSSTRecord lsrec = (LabelSSTRecord)record;
        ReadCache readCache = xlsReadContext.readWorkbookHolder().getReadCache();
        Map<Integer, Cell> cellMap = xlsReadContext.xlsReadSheetHolder().getCellMap();
        if (readCache == null) {
            cellMap.put((int)lsrec.getColumn(), ReadCellData.newEmptyInstance(lsrec.getRow(), (int)lsrec.getColumn()));
            return;
        }
        String data = readCache.get(lsrec.getSSTIndex());
        if (data == null) {
            cellMap.put((int)lsrec.getColumn(), ReadCellData.newEmptyInstance(lsrec.getRow(), (int)lsrec.getColumn()));
            return;
        }
        if (xlsReadContext.currentReadHolder().globalConfiguration().getAutoTrim()) {
            data = data.trim();
        }
        cellMap.put((int)lsrec.getColumn(), ReadCellData.newInstance(data, lsrec.getRow(), (int)lsrec.getColumn()));
        xlsReadContext.xlsReadSheetHolder().setTempRowType(RowTypeEnum.DATA);
    }
}
