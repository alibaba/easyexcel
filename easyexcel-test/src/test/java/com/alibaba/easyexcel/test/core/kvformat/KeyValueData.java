package com.alibaba.easyexcel.test.core.kvformat;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.KeyValueFormat;
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
public class KeyValueData {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty(value = "单位类型")
    @KeyValueFormat(targetClass = DepartmentEnum.class, javaify = "getKeyByDesc", excelify = "getDescByKey")
    private Integer ouType;

    @ExcelProperty(value = "性别")
    @KeyValueFormat(targetClass = SexTypeEnum.class, javaify = "getKeyByDesc", excelify = "getDescByKey")
    private String sexType;

}
