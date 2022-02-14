package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelDynamicClass;
import com.alibaba.excel.annotation.ExcelDynamicColumn;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author helei01_gza
 * @since 2022/2/11
 */
@Getter
@Setter
@EqualsAndHashCode
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
@ContentFontStyle(fontHeightInPoints = 20)
public class DemoDynamicData {

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;

    @ExcelDynamicColumn
    private DynamicData[] dynamicArray;

    @ExcelDynamicColumn
    private List<DynamicData> dynamicDataList;

    @ExcelProperty("数字标题")
    private Double doubleData;

    @Getter
    @Setter
    @EqualsAndHashCode
    @ExcelDynamicClass
    // 头背景设置成红色 IndexedColors.RED.getIndex()
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20
    @HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
    @ContentFontStyle(fontHeightInPoints = 20)
    public static class DynamicData {
        // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
        @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 14)
        // 字符串的头字体设置成20
        @HeadFontStyle(fontHeightInPoints = 30)
        // 字符串的内容的背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
        @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
        // 字符串的内容字体设置成20
        @ContentFontStyle(fontHeightInPoints = 30)
        @ExcelProperty("动态字符串标题")
        private String string;

        @NumberFormat("#.##%")
        @ExcelProperty("动态数字标题")
        private Double doubleData;
    }

}


