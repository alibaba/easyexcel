package com.alibaba.excel.write.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Write sheet
 *
 * @author jipengfei
 */
@Getter
@Setter
@EqualsAndHashCode
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
