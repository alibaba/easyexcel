package com.alibaba.excel.analysis.v03.handlers;

import java.util.LinkedHashMap;

import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.metadata.Cell;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class DummyRecordRecordHandler implements IgnorableXlsRecordHandler {
    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        if (record instanceof LastCellOfRowDummyRecord) {
            // End of this row
            LastCellOfRowDummyRecord lcrdr = (LastCellOfRowDummyRecord)record;
            xlsReadContext.rowIndex(lcrdr.getRow());
            xlsReadContext.readRowHolder(new ReadRowHolder(lcrdr.getRow(), xlsReadContext.tempRowType(),
                xlsReadContext.readSheetHolder().getGlobalConfiguration()));
            xlsReadContext.analysisEventProcessor().endRow(xlsReadContext);
            xlsReadContext.cellMap(new LinkedHashMap<Integer, Cell>());
        } else if (record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mcdr = (MissingCellDummyRecord)record;
            xlsReadContext.cellMap().put(mcdr.getColumn(), CellData.newEmptyInstance(mcdr.getRow(), mcdr.getColumn()));
        }
    }
}
