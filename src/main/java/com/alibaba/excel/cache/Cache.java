package com.alibaba.excel.cache;

/**
 * cache
 * 
 * @author zhuangjiaju
 */
public interface Cache {

    /**
     * put
     * 
     * @param key
     * @param value
     */
    void put(Integer key, String value);

    /**
     * get
     * 
     * @param key
     * @return
     */
    String get(Integer key);

    void finish();
}
