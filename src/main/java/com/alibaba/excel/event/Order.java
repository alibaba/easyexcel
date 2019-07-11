package com.alibaba.excel.event;

/**
 * Implement this interface when sorting
 * 
 * @author zhuangjiaju
 */
public interface Order {
    /**
     * The smaller the first implementation
     *
     * @return
     */
    int order();
}
