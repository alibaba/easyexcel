package com.alibaba.excel.write.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * table
 *
 * @author jipengfei
 */
@Getter
@Setter
@EqualsAndHashCode
public class WriteTable extends WriteBasicParameter {
    /**
     * Starting from 0
     */
    private Integer tableNo;
}
