package com.alibaba.excel.cache;

import com.alibaba.excel.context.AnalysisContext;

/**
 * Read cache
 * 
 * @author zhuangjiaju
 */
public interface ReadCache {

    /**
     * Initialize cache
     * 
     * @param analysisContext
     */
    void init(AnalysisContext analysisContext);

    /**
     * Automatically generate the key and put it in the cache.Key start from 0
     * 
     * @param value
     */
    void put(String value);

    /**
     * Get value
     * 
     * @param key
     * @return
     */
    String get(Integer key);

    /**
     * It's called when all the values are put in
     */
    void putFinished();

    /**
     * Called when the excel read is complete
     */
    void destroy();

}
