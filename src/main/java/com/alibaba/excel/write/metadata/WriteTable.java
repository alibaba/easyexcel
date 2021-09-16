package com.alibaba.excel.write.metadata;

import lombok.Data;

/**
 * table
 *
 * @author jipengfei
 */
@Data
public class WriteTable extends WriteBasicParameter {
    /**
     * Starting from 0
     */
    private Integer tableNo;
}
