package com.alibaba.excel.cache;

import com.alibaba.excel.context.AnalysisContext;

/**
 * Read cache
 *
 * @author Jiaju Zhuang
 */
public interface ReadCache {

    /**
     * Initialize cache
     *
     * @param analysisContext
     *            A context is the main anchorage point of a excel reader.
     */
    void init(AnalysisContext analysisContext);

    /**
     * Automatically generate the key and put it in the cache.Key start from 0
     *
     * @param value
     *            Cache value
     */
    void put(String value);

    /**
     * Get value
     *
     * @param key
     *            Index
     * @return Value
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
