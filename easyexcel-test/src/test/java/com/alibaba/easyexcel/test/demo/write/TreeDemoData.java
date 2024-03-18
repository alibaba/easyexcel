package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.ExcelSubData;
import lombok.Data;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TreeDemoData {

    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;

    @ExcelSubData(fillForegroundColors = {IndexedColors.GREY_25_PERCENT, IndexedColors.LIGHT_TURQUOISE})
    private List<TreeDemoData> sub = new ArrayList<>();

    public void addSub(TreeDemoData data) {
        sub.add(data);
    }


    public TreeDemoData(String string, Double doubleData) {
        this.string = string;
        this.date = new Date();
        this.doubleData = doubleData;
    }
}
