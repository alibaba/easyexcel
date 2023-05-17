package com.alibaba.easyexcel.test.core.cache;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CacheInvokeMemoryData {
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Long age;
}
