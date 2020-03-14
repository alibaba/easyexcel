package com.alibaba.excel.analysis.v03.handlers;

import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.TextObjectRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.analysis.v03.IgnorableXlsRecordHandler;
import com.alibaba.excel.context.xls.XlsReadContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.read.metadata.holder.xls.XlsReadSheetHolder;

/**
 * Record handler
 *
 * @author Jiaju Zhuang
 */
public class TextObjectRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextObjectRecordHandler.class);

    @Override
    public boolean support(XlsReadContext xlsReadContext, Record record) {
        return xlsReadContext.readWorkbookHolder().getExtraReadSet().contains(CellExtraTypeEnum.COMMENT);
    }

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        TextObjectRecord tor = (TextObjectRecord)record;
        XlsReadSheetHolder xlsReadSheetHolder = xlsReadContext.xlsReadSheetHolder();
        Integer tempObjectIndex = xlsReadSheetHolder.getTempObjectIndex();
        if (tempObjectIndex == null) {
            LOGGER.debug("tempObjectIndex is null.");
            return;
        }
        xlsReadSheetHolder.getObjectCacheMap().put(tempObjectIndex, tor.getStr().getString());
        xlsReadSheetHolder.setTempObjectIndex(null);
    }
}
