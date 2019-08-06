package com.alibaba.excel.cache;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;

/**
 *
 * Putting temporary data directly into a map is a little more efficient but very memory intensive
 *
 * @author Jiaju Zhuang
 */
public class MapCache implements ReadCache {
    private Map<Integer, String> cache = new HashMap<Integer, String>();
    private int index = 0;

    @Override
    public void init(AnalysisContext analysisContext) {}

    @Override
    public void put(String value) {
        cache.put(index++, value);
    }

    @Override
    public String get(Integer key) {
        if (key == null || key < 0) {
            return null;
        }
        return cache.get(key);
    }

    @Override
    public void putFinished() {}

    @Override
    public void destroy() {}

}
