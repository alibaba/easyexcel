package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.TextObjectRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.XlsReadContext;

/**
 * Record handler
 *
 * @author Jiaju Zhuang
 */
public class TextObjectRecordHandler implements IgnorableXlsRecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextObjectRecordHandler.class);

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        TextObjectRecord tor = (TextObjectRecord)record;
        Integer tempObjectIndex = xlsReadContext.tempObjectIndex();
        if (tempObjectIndex == null) {
            LOGGER.debug("tempObjectIndex is null.");
            return;
        }
        xlsReadContext.objectCacheMap().put(tempObjectIndex, tor.getStr().getString());
        xlsReadContext.tempObjectIndex(null);
    }
}
