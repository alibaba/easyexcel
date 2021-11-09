package com.alibaba.easyexcel.test.core.fill.annotation;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(100)
public class FillAnnotationData {
    @ExcelProperty("日期")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private Date date;

    @ExcelProperty(value = "数字")
    @NumberFormat("#.##%")
    private Double number;

    @ContentLoopMerge(columnExtend = 2)
    @ExcelProperty("字符串1")
    private String string1;
    @ExcelProperty("字符串2")
    private String string2;
}
