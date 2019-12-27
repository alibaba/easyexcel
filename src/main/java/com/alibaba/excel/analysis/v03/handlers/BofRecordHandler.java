package com.alibaba.excel.analysis.v03.handlers;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;

import com.alibaba.excel.analysis.v03.XlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.SheetUtils;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BofRecordHandler implements XlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BOFRecord br = (BOFRecord)record;
        if (br.getType() != BOFRecord.TYPE_WORKSHEET) {
            return;
        }
        // Init read sheet Data
        initReadSheetDataList(xlsReadContext);
        Integer readSheetIndex = xlsReadContext.readSheetIndex();
        if (readSheetIndex == null) {
            readSheetIndex = 0;
            xlsReadContext.readSheetIndex(readSheetIndex);
        }

        ReadSheet readSheet = xlsReadContext.readSheetDataList().get(readSheetIndex);
        assert readSheet != null : "Can't find the sheet.";
        // Copy the parameter to the current sheet
        readSheet = SheetUtils.match(readSheet, xlsReadContext);
        if (readSheet != null) {
            xlsReadContext.currentSheet0(readSheet);
            xlsReadContext.ignoreRecord(Boolean.FALSE);
        } else {
            xlsReadContext.ignoreRecord(Boolean.TRUE);
        }
        // Go read the next one
        xlsReadContext.readSheetIndex(xlsReadContext.readSheetIndex() + 1);
    }

    private void initReadSheetDataList(XlsReadContext xlsReadContext) {
        if (xlsReadContext.readSheetDataList() != null) {
            return;
        }
        BoundSheetRecord[] boundSheetRecords =
            BoundSheetRecord.orderByBofPosition(xlsReadContext.boundSheetRecordList());
        List<ReadSheet> readSheetDataList = new ArrayList<ReadSheet>();
        for (int i = 0; i < boundSheetRecords.length; i++) {
            BoundSheetRecord boundSheetRecord = boundSheetRecords[i];
            ReadSheet readSheet = new ReadSheet(i, boundSheetRecord.getSheetname());
            readSheetDataList.add(readSheet);
        }
        xlsReadContext.readSheetDataList(readSheetDataList);
        // Just need to get the list of sheets
        if (!xlsReadContext.needReadSheet()) {
            throw new ExcelAnalysisStopException("Just need to get the list of sheets.");
        }
    }
}
