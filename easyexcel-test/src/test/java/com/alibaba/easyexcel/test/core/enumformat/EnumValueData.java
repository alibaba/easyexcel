package com.alibaba.easyexcel.test.core.enumformat;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.EnumFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiajiafu
 * @since 2022/7/14
 */
@Getter
@Setter
@EqualsAndHashCode
public class EnumValueData {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty(value = "单位类型")
    @EnumFormat(targetClass = OuTypeEnum.class, convertToExcelDataMethod = "getDescByKey")
    private Integer ouType;

    @ExcelProperty(value = "性别")
    @EnumFormat(targetClass = SexTypeEnum.class, convertToExcelDataMethod = "getDescByKey")
    private String sexType;

}
