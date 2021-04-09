package com.alibaba.easyexcel.test.core.style;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;
import lombok.Data;

/**
 * @author Pengliang Zhao
 */
@Data
@OnceAbsoluteMerge(firstRowIndex = (short)0, lastRowIndex = (short)2, firstColumnIndex = (short)0, lastColumnIndex = (short)2)
public class OnceAbsoluteMergeData {
    @ExcelProperty(value = "洗漱种类", index = 0)
    private String category;
    @ExcelProperty(value = "毛巾名称", index = 1)
    private String towel;
    @ExcelProperty(value = "毛巾尺寸", index = 2)
    private String size;
}
