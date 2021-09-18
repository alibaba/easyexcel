package com.alibaba.excel.enums;

import lombok.Getter;

/**
 * Default values cannot be used for annotations.
 * So an additional an enumeration to determine whether the user has added the enumeration.
 *
 * @author Jiaju Zhuang
 */
@Getter
public enum BooleanEnum {
    /**
     * NULL
     */
    DEFAULT(null),
    /**
     * TRUE
     */
    TRUE(Boolean.TRUE),
    /**
     * FALSE
     */
    FALSE(Boolean.FALSE),
    ;

    Boolean booleanValue;

    BooleanEnum(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

}
