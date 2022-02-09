package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;

/**
 * Configuration from annotations
 *
 * @author Jiaju Zhuang
 */
public class OnceAbsoluteMergeProperty {
    /**
     * First row
     */
    private int firstRowIndex;
    /**
     * Last row
     */
    private int lastRowIndex;
    /**
     * First column
     */
    private int firstColumnIndex;
    /**
     * Last row
     */
    private int lastColumnIndex;

    public OnceAbsoluteMergeProperty(int firstRowIndex, int lastRowIndex, int firstColumnIndex, int lastColumnIndex) {
        this.firstRowIndex = firstRowIndex;
        this.lastRowIndex = lastRowIndex;
        this.firstColumnIndex = firstColumnIndex;
        this.lastColumnIndex = lastColumnIndex;
    }

    public static OnceAbsoluteMergeProperty build(OnceAbsoluteMerge onceAbsoluteMerge) {
        if (onceAbsoluteMerge == null) {
            return null;
        }
        return new OnceAbsoluteMergeProperty(onceAbsoluteMerge.firstRowIndex(), onceAbsoluteMerge.lastRowIndex(),
            onceAbsoluteMerge.firstColumnIndex(), onceAbsoluteMerge.lastColumnIndex());
    }

    public int getFirstRowIndex() {
        return firstRowIndex;
    }

    public void setFirstRowIndex(int firstRowIndex) {
        this.firstRowIndex = firstRowIndex;
    }

    public int getLastRowIndex() {
        return lastRowIndex;
    }

    public void setLastRowIndex(int lastRowIndex) {
        this.lastRowIndex = lastRowIndex;
    }

    public int getFirstColumnIndex() {
        return firstColumnIndex;
    }

    public void setFirstColumnIndex(int firstColumnIndex) {
        this.firstColumnIndex = firstColumnIndex;
    }

    public int getLastColumnIndex() {
        return lastColumnIndex;
    }

    public void setLastColumnIndex(int lastColumnIndex) {
        this.lastColumnIndex = lastColumnIndex;
    }
}
