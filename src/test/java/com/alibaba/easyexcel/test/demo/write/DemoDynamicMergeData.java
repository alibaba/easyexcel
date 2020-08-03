package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 动态合并的数据类
 *
 * @author ZhiPeng Xu
 */
@Builder
@Data
public class DemoDynamicMergeData {

    @ExcelProperty(value = "省", index = 0)
    private final String province;

    @ExcelProperty(value = "市", index = 1)
    private final String city;

    @ExcelProperty(value = "区", index = 2)
    private final String district;
}
