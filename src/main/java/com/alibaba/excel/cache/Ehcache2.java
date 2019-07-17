package com.alibaba.excel.cache;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default cache
 * 
 * @author zhuangjiaju
 */
public class Ehcache2 implements Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(Ehcache2.class);

    int index = 0;
    // private org.ehcache.Cache<Integer, String> cache;

    private Map<Integer, String> cache = new HashMap<Integer, String>();

    public Ehcache2() {}

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
