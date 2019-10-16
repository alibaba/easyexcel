package com.alibaba.excel.converters.string;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.util.NumberUtils;
import org.apache.poi.ss.usermodel.DateUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * String and number converter
 *
 * @author Jiaju Zhuang
 */
public class StringNumberConverter implements Converter<String> {

    private static final String POINT = ".";

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        // If there are "DateTimeFormat", read as date
        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
            return DateUtils.format(
                DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                    contentProperty.getDateTimeFormatProperty().getUse1904windowing(), null),
                contentProperty.getDateTimeFormatProperty().getFormat());
        }
        // If there are "NumberFormat", read as number
        if (contentProperty != null && contentProperty.getNumberFormatProperty() != null) {
            return NumberUtils.format(cellData.getNumberValue(), contentProperty);
        }
        // Excel defines formatting
        if (cellData.getDataFormat() != null) {
            if (DateUtil.isADateFormat(cellData.getDataFormat(), cellData.getDataFormatString()) || isDateFormat(
                cellData.getDataFormat(), cellData.getDataFormatString())) {
                String value = cellData.getNumberValue().toPlainString();
                if (value.contains(POINT)) {
                    // 分两种情况 年月日 时分秒  /  时分秒
                    if (cellData.getNumberValue().doubleValue() > 1) {
                        return DateUtils.format(DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                            globalConfiguration.getUse1904windowing(), null), DateUtils.DATE_FORMAT_19);
                    } else {
                        return DateUtils.format(DateUtil.getJavaDate(cellData.getNumberValue().doubleValue(),
                            globalConfiguration.getUse1904windowing(), null), DateUtils.DATE_FORMAT_8_TIME);
                    }
                } else {
                    // 不包含小数点，只有年月日
                    return DateUtils.format(DateUtil
                        .getJavaDate(cellData.getNumberValue().doubleValue(), globalConfiguration.getUse1904windowing(),
                            null), DateUtils.DATE_FORMAT_10);
                }
            } else {
                return NumberUtils.format(cellData.getNumberValue(), contentProperty);
            }
        }
        // Default conversion number
        return NumberUtils.format(cellData.getNumberValue(), contentProperty);
    }

    /**
     * 日期类型
     */
    List<Integer> dateTypeList = Arrays
        .asList(14, 15, 16, 17, 22, 30, 31, 57, 58, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189,
            190, 191, 192, 193, 194, 195, 196, 197, 198, 199);

    /**
     * 时间类型
     */
    List<Integer> timeTypeList = Arrays
        .asList(18, 19, 20, 21, 32, 33, 45, 46, 47, 55, 56, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186);

    /**
     * 特殊类型
     */
    List<Integer> specialTypeList = Arrays.asList(176, 177, 178, 179, 180);

    /**
     * 校验单元格内容是否为时间
     * @param formatIndex
     * @param formatString
     * @return
     */
    private boolean isDateFormat(int formatIndex, String formatString){
        if(dateTypeList.contains(formatIndex) || timeTypeList.contains(formatIndex)){
            //            if(specialTypeList.contains(formatIndex)){
            //                if(formatString.contains("mm") || formatString.contains("m") || "yy/m/d".equals() || formatString.equals("m/d")){
            //                    return true;
            //                }
            //            }else {
            //                return true;
            //            }
            return true;
        }
        return false;
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new CellData(new BigDecimal(value));
    }
}
