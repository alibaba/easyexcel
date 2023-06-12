package com.alibaba.excel.write.metadata;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import lombok.Data;

/**
 * @author hengyuan.cao
 */
@Data
public class RowDataInfo {

    WriteCellStyle rowStyle;
    int depth = 0;
}
