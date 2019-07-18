package com.alibaba.excel.cache;

/**
 * Read cache
 * 
 * @author zhuangjiaju
 */
public interface ReadCache {
    /**
     * Automatically generate the key and put it in the cache.Key start from 0
     * 
     * @param value
     */
    void put(String value);

    /**
     * Get
     * 
     * @param key
     * @return
     */
    String get(Integer key);

    /**
     * 所有
     */
    void putFinished();

    /**
     *
     */
    void destroy();

}
