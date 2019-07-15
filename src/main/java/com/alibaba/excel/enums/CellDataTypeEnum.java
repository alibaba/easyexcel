package com.alibaba.excel.enums;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.util.StringUtils;

/**
 * excel internal data type
 *
 * @author zhuangjiaju
 */
public enum CellDataTypeEnum {

    /**
     * string
     */
    STRING,
    /**
     * inlineString
     */
    INLINE_STRING,
    /**
     * number
     */
    NUMBER,
    /**
     * boolean
     */
    BOOLEAN,
    /**
     * empty
     */
    EMPTY,
    /**
     * error
     */
    ERROR;

    private static final Map<String, CellDataTypeEnum> TYPE_ROUTING_MAP = new HashMap<String, CellDataTypeEnum>(8);
    static {
        TYPE_ROUTING_MAP.put("s", STRING);
        TYPE_ROUTING_MAP.put("inlineStr", INLINE_STRING);
        TYPE_ROUTING_MAP.put("e", ERROR);
        TYPE_ROUTING_MAP.put("b", BOOLEAN);
    }

    /**
     * Build data types
     * 
     * @param cellType
     * @return
     */
    public static CellDataTypeEnum buildFromCellType(String cellType) {
        if (StringUtils.isEmpty(cellType)) {
            return EMPTY;
        }
        return TYPE_ROUTING_MAP.get(cellType);
    }
}
