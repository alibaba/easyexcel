package com.alibaba.excel.enums;

/**
 * 合并单元格类型
 * 1 水平方向合并
 * 2 垂直方向合并
 * 3 先水平，然后垂直方向合并
 * @author Lucas
 * @date 2020-1-17
 * @since 1.0.0L
 */
public enum MergeTypeEnum {
    HORIZONTAL_MERGE(1),
    VERTICAL_MERGE(2),
    CENTER_MERGE(3);

    private int type;

    MergeTypeEnum(int type){
        this.type = type;
        this.index = type;
    }

    public int index;

    public int getType() {
        return type;
    }
}
