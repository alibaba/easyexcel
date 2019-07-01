package com.alibaba.excel.write;

public interface MergeStrategy {
    int getFirstRow();
    int getLastRow();
    int getFirstCol();
    int getLastCol();
}
