package com.alibaba.excel.util;

import com.alibaba.excel.exception.ExcelRuntimeException;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.BitField;
import org.apache.poi.util.BitFieldFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.lang.reflect.Field;

/**
 * utils
 *
 * @author Jiaju Zhuang
 */
public class PoiUtils {

    /**
     * Whether to customize the height
     */
    public static final BitField CUSTOM_HEIGHT = BitFieldFactory.getInstance(0x640);

    private static Field ROW_RECORD_FIELD;

    /**
     * Whether to customize the height
     *
     * @param row row
     * @return
     */
    public static boolean customHeight(Row row) {
        if (row instanceof XSSFRow) {
            XSSFRow xssfRow = (XSSFRow) row;
            return xssfRow.getCTRow().getCustomHeight();
        }
        if (row instanceof HSSFRow) {
            HSSFRow hssfRow = (HSSFRow) row;
            try {
                if (ROW_RECORD_FIELD == null) {
                    initRowRecordField();
                }
                RowRecord record = (RowRecord) ROW_RECORD_FIELD.get(hssfRow);
                return CUSTOM_HEIGHT.getValue(record.getOptionFlags()) == 1;
            } catch (IllegalAccessException ignore) {
            }
        }
        return false;
    }


    private static void initRowRecordField() {
        try {
            ROW_RECORD_FIELD = HSSFRow.class.getDeclaredField("row");
            ROW_RECORD_FIELD.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new ExcelRuntimeException(e);
        }
    }
}
