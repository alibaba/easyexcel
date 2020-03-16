package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.StringRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadSheetHolder;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class StringRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringRecordHandler.class);

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        // String for formula
        StringRecord srec = (StringRecord)record;
        XlsReadSheetHolder xlsReadSheetHolder = xlsReadContext.xlsReadSheetHolder();
        CellData tempCellData = xlsReadSheetHolder.getTempCellData();
        if (tempCellData == null) {
            LOGGER.warn("String type formula but no value found.");
            return;
        }
        tempCellData.setStringValue(srec.getString());
        xlsReadSheetHolder.getCellMap().put(tempCellData.getColumnIndex(), tempCellData);
        xlsReadSheetHolder.setTempCellData(null);
    }
}
