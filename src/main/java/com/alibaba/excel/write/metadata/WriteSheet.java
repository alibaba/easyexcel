package com.alibaba.excel.write.metadata;

import lombok.Data;

/**
 * Write sheet
 *
 * @author jipengfei
 */
@Data
public class WriteSheet extends WriteBasicParameter {
    /**
     * Starting from 0
     */
    private Integer sheetNo;
    /**
     * sheet name
     */
    private String sheetName;
}
