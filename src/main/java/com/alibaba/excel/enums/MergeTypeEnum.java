package com.alibaba.excel.enums;

/**
 * 合并单元格类型
 * Merge cell types
 * 1 水平方向合并
 * Merge horizontally
 * 2 垂直方向合并
 * Merge vertically
 * 3 先水平，然后垂直方向合并
 * Merge first horizontally, then vertically
 * @author Lucas
 * @date 2020-1-17
 * @since 1.0.0L
 */
public enum MergeTypeEnum {
    /**
     * 水平方向合并
     * Merge horizontally
     */
    HORIZONTAL_MERGE(1),
    /**
     * 垂直方向合并
     * Merge vertically
     */
    VERTICAL_MERGE(2),
    /**
     * 先水平，然后垂直方向合并
     * Merge first horizontally, then vertically
     */
    CENTER_MERGE(3);

    /**
     * 类型
     * type
     */
    private int type;
    /**
     * 获取索引值
     * Get the index value
     */
    public int index;

    MergeTypeEnum(int type){
        this.type = type;
        this.index = type;
    }

    /**
     * 获取类型值
     * Get type value
     */
    public int getType() {
        return type;
    }
}
