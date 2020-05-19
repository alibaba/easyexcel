package com.alibaba.easyexcel.test.core.style;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Pengliang Zhao
 */
@Data
public class LoopMergeData {
    @ExcelProperty(value = "洗漱种类", index = 0)
    private String category;
    @ExcelProperty(value = "毛巾名称", index = 1)
    private String towel;
    @ExcelProperty(value = "毛巾尺寸", index = 2)
    private String size;
}
