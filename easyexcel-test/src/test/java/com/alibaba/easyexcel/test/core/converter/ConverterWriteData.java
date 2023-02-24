package com.alibaba.easyexcel.test.core.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.data.WriteCellData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class ConverterWriteData {
    @ExcelProperty("日期")
    private Date date;
    @ExcelProperty("本地日期")
    private LocalDate localDate;
    @ExcelProperty("本地日期时间")
    private LocalDateTime localDateTime;
    @ExcelProperty("布尔")
    private Boolean booleanData;
    @ExcelProperty("大数")
    private BigDecimal bigDecimal;
    @ExcelProperty("大整数")
    private BigInteger bigInteger;
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
    private WriteCellData<?> cellData;
}
