package com.alibaba.easyexcel.test.core.converter;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.CellData;

import lombok.Data;

/**
 * @author Jiaju Zhuang
 */
@Data
public class ConverterData {
    @ExcelProperty("日期")
    private Date date;
    @ExcelProperty("布尔")
    private Boolean booleanData;
    @ExcelProperty("大数")
    private BigDecimal bigDecimal;
    @ExcelProperty("长整型")
    private long longData;
    @ExcelProperty("整型")
    private Integer integerData;
    @ExcelProperty("短整型")
    private Short shortData;
    @ExcelProperty("字节型")
    private Byte byteData;
    @ExcelProperty("双精度浮点型")
    private double doubleData;
    @ExcelProperty("浮点型")
    private Float floatData;
    @ExcelProperty("字符串")
    private String string;
    @ExcelProperty("自定义")
    private CellData cellData;
}
