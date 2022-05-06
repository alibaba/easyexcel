package com.alibaba.easyexcel.test.core.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class ReadAllConverterData {
    private BigDecimal bigDecimalBoolean;
    private BigDecimal bigDecimalNumber;
    private BigDecimal bigDecimalString;
    private BigInteger bigIntegerBoolean;
    private BigInteger bigIntegerNumber;
    private BigInteger bigIntegerString;
    private Boolean booleanBoolean;
    private Boolean booleanNumber;
    private Boolean booleanString;
    private Byte byteBoolean;
    private Byte byteNumber;
    private Byte byteString;
    private Date dateNumber;
    private Date dateString;
    private LocalDateTime localDateTimeNumber;
    private LocalDateTime localDateTimeString;
    private Double doubleBoolean;
    private Double doubleNumber;
    private Double doubleString;
    private Float floatBoolean;
    private Float floatNumber;
    private Float floatString;
    private Integer integerBoolean;
    private Integer integerNumber;
    private Integer integerString;
    private Long longBoolean;
    private Long longNumber;
    private Long longString;
    private Short shortBoolean;
    private Short shortNumber;
    private Short shortString;
    private String stringBoolean;
    private String stringNumber;
    private String stringString;
    private String stringError;
    private String stringFormulaNumber;
    private String stringFormulaString;
    private String stringNumberDate;
}
