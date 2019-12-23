package com.alibaba.excel.util;

/**
 * Thread local cache in the current tool class.
 *
 * @author Jiaju Zhuang
 **/
public interface ThreadLocalCachedUtils {

    /**
     * Remove remove thread local cached.
     */
    void removeThreadLocalCache();
}
