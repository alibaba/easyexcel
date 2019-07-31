package com.alibaba.easyexcel.test.core.converter;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author zhuangjiaju
 */
@Data
public class ReadConverterData {
    @ExcelProperty("日期")
    private Date date;
    @ExcelProperty("日期字符串")
    private String dateString;
    @ExcelProperty("布尔")
    private Boolean booleanData;
    @ExcelProperty("布尔字符串")
    private String booleanString;
    @ExcelProperty("大数")
    private BigDecimal bigDecimal;
    @ExcelProperty("大数字符串")
    private String bigDecimalString;
    @ExcelProperty("长整型")
    private Long longData;
    @ExcelProperty("长整型字符串")
    private String longString;
    @ExcelProperty("整型")
    private Integer integerData;
    @ExcelProperty("整型字符串")
    private String integerString;
    @ExcelProperty("短整型")
    private Short shortData;
    @ExcelProperty("短整型字符串")
    private String shortString;
    @ExcelProperty("字节型")
    private Byte byteData;
    @ExcelProperty("字节型字符串")
    private String byteString;
    @ExcelProperty("双精度浮点型")
    private Double doulbleData;
    @ExcelProperty("双精度浮点型字符串")
    private String doulbleString;
    @ExcelProperty("浮点型")
    private Float FloatData;
    @ExcelProperty("浮点型字符串")
    private String FloatString;
    @ExcelProperty("字符串")
    private String string;
}
