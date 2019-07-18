package com.alibaba.excel.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Putting temporary data directly into a map is a little more efficient but very memory intensive
 * 
 * @author zhuangjiaju
 */
public class MapCache implements ReadCache {
    private Map<Integer, String> cache = new HashMap<Integer, String>();

    public MapCache() {}

    @Override
    public void put(Integer key, String value) {
        cache.put(key, value);
    }

    @Override
    public String get(Integer key) {
        return cache.get(key);
    }

    @Override
    public void finish() {}
}
