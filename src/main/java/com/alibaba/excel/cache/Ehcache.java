package com.alibaba.excel.cache;

import java.io.File;
import java.util.Map;

import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import com.alibaba.excel.util.POITempFile;

/**
 * Default cache
 * 
 * @author zhuangjiaju
 */
public class Ehcache implements Cache {
    private org.ehcache.Cache<Integer, Map<Integer, String>> cache;

    public Ehcache() {
        File file = POITempFile.createCacheTmpFile();
        PersistentCacheManager persistentCacheManager =
            CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerBuilder.persistence(file))
                .withCache("cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB)))
                .build(true);
        this.cache = persistentCacheManager.getCache("cache", Integer.class, String.class);
    }

    @Override
    public void put(Integer key, String value) {
        cache.put(key, value);
    }

    @Override
    public String get(Integer key) {
        return cache.get(key);
    }

    @Override
    public void finish() {

    }
}
