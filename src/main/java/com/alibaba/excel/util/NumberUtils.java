package com.alibaba.excel.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * Number utils
 * 
 * @author zhuangjiaju
 */
public class NumberUtils {
    private NumberUtils() {}

    /**
     * format
     *
     * @param num
     * @param contentProperty
     * @return
     */
    public static String format(Number num, ExcelContentProperty contentProperty) {
        if (contentProperty.getNumberFormatProperty() == null
            || StringUtils.isEmpty(contentProperty.getNumberFormatProperty().getFormat())) {
            return num.toString();
        }
        String format = contentProperty.getNumberFormatProperty().getFormat();
        RoundingMode roundingMode = contentProperty.getNumberFormatProperty().getRoundingMode();
        DecimalFormat decimalFormat = new DecimalFormat(format);
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(num);
    }

    /**
     * format
     * 
     * @param num
     * @param contentProperty
     * @return
     */
    public static CellData formatToCellData(Number num, ExcelContentProperty contentProperty) {
        return new CellData(format(num, contentProperty));
    }
}
