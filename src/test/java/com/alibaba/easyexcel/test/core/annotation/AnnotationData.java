package com.alibaba.easyexcel.test.core.annotation;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author zhuangjiaju
 */
@Data
public class AnnotationData {
    @ExcelProperty("日期")
    private Date name;
}
