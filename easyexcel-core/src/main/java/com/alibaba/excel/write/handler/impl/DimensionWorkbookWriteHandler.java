package com.alibaba.excel.write.handler.impl;

import java.lang.reflect.Field;
import java.util.Map;

import com.alibaba.excel.util.FieldUtils;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;

/**
 * Handle the problem of unable to write dimension.
 *
 * https://github.com/alibaba/easyexcel/issues/1282
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class DimensionWorkbookWriteHandler implements WorkbookWriteHandler {

    private static final String XSSF_SHEET_MEMBER_VARIABLE_NAME = "_sh";
    private static final Field XSSF_SHEET_FIELD = FieldUtils.getField(SXSSFSheet.class, XSSF_SHEET_MEMBER_VARIABLE_NAME,
        true);

    @Override
    public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {
        if (writeWorkbookHolder == null || writeWorkbookHolder.getWorkbook() == null) {
            return;
        }
        if (!(writeWorkbookHolder.getWorkbook() instanceof SXSSFWorkbook)) {
            return;
        }

        Map<Integer, WriteSheetHolder> writeSheetHolderMap = writeWorkbookHolder.getHasBeenInitializedSheetIndexMap();
        if (MapUtils.isEmpty(writeSheetHolderMap)) {
            return;
        }
        for (WriteSheetHolder writeSheetHolder : writeSheetHolderMap.values()) {
            if (writeSheetHolder.getSheet() == null || !(writeSheetHolder.getSheet() instanceof SXSSFSheet)) {
                continue;
            }
            SXSSFSheet sxssfSheet = ((SXSSFSheet)writeSheetHolder.getSheet());
            XSSFSheet xssfSheet;
            try {
                xssfSheet = (XSSFSheet)XSSF_SHEET_FIELD.get(sxssfSheet);
            } catch (IllegalAccessException e) {
                log.debug("Can not found _sh.", e);
                continue;
            }
            if (xssfSheet == null) {
                continue;
            }
            CTWorksheet ctWorksheet = xssfSheet.getCTWorksheet();
            if (ctWorksheet == null) {
                continue;
            }
            int headSize = 0;
            if (MapUtils.isNotEmpty(writeSheetHolder.getExcelWriteHeadProperty().getHeadMap())) {
                headSize = writeSheetHolder.getExcelWriteHeadProperty().getHeadMap().size();
                if (headSize > 0) {
                    headSize--;
                }
            }
            Integer lastRowIndex = writeSheetHolder.getLastRowIndex();
            if (lastRowIndex == null) {
                lastRowIndex = 0;
            }

            ctWorksheet.getDimension().setRef(
                "A1:" + CellReference.convertNumToColString(headSize) + (lastRowIndex + 1));
        }
    }
}
