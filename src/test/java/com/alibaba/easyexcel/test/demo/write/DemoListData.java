package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelList;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.DynamicDirectionEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author dff on 2020-09-09
 */
@Data
public class DemoListData {

    @ExcelProperty({"一级标题1", "字符串标题"})
    private String string;
    @ExcelProperty({"一级标题1", "日期标题"})
    private Date date;
    @ExcelProperty({"一级标题2", "数字标题"})
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    @ExcelList
    @ExcelProperty({"一级标题3", "动态行标题"})
    private List dynamicRows;

    @ExcelList(direction = DynamicDirectionEnum.ORIENTATION)
    @ExcelProperty({"一级标题3", "动态列标题"})
    private List dynamicCols;
}
