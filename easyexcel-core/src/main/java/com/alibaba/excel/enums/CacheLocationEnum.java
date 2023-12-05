package com.alibaba.excel.enums;

/**
 * cache locaciton
 *
 * @author Jiaju Zhuang
 **/
public enum CacheLocationEnum {
    /**
     * The cache will be stored in {@code ThreadLocal}, and will be cleared when the excel read and write is completed.
     */
    THREAD_LOCAL,

    /**
     * The cache will not be cleared unless the app is stopped.
     */
    MEMORY,

    /**
     * No caching.It may lose some of performance.
     */
    NONE;
}
