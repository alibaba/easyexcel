package com.alibaba.excel.cache;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.util.FileUtils;

/**
 * Default cache
 *
 * @author Jiaju Zhuang
 */
public class Ehcache implements ReadCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ehcache.class);
    private static final int BATCH_COUNT = 1000;
    private static final int DEBUG_WRITE_SIZE = 100 * 10000;
    private static final int DEBUG_CACHE_MISS_SIZE = 1000;
    /**
     * Key index
     */
    private int index = 0;
    private HashMap<Integer, String> dataMap = new HashMap<>(BATCH_COUNT * 4 / 3 + 1);
    private static final CacheManager FILE_CACHE_MANAGER;
    private static final CacheConfiguration<Integer, HashMap> FILE_CACHE_CONFIGURATION;
    private static final CacheManager ACTIVE_CACHE_MANAGER;
    private final CacheConfiguration<Integer, HashMap> activeCacheConfiguration;
    /**
     * Bulk storage data
     */
    private org.ehcache.Cache<Integer, HashMap> fileCache;
    /**
     * Currently active cache
     */
    private org.ehcache.Cache<Integer, HashMap> activeCache;
    private String cacheAlias;
    /**
     * Count the number of cache misses
     */
    private int cacheMiss = 0;

    public Ehcache(int maxCacheActivateSize) {
        activeCacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(Integer.class, HashMap.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(maxCacheActivateSize, MemoryUnit.MB))
            .withSizeOfMaxObjectGraph(1000 * 1000L).withSizeOfMaxObjectSize(maxCacheActivateSize, MemoryUnit.MB)
            .build();
    }

    static {
        File cacheFile = FileUtils.createCacheTmpFile();
        FILE_CACHE_MANAGER =
            CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerBuilder.persistence(cacheFile)).build(true);
        ACTIVE_CACHE_MANAGER = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        FILE_CACHE_CONFIGURATION = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(Integer.class, HashMap.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB))
            .withSizeOfMaxObjectGraph(1000 * 1000L).withSizeOfMaxObjectSize(10, MemoryUnit.GB).build();
    }

    @Override
    public void init(AnalysisContext analysisContext) {
        cacheAlias = UUID.randomUUID().toString();
        fileCache = FILE_CACHE_MANAGER.createCache(cacheAlias, FILE_CACHE_CONFIGURATION);
        activeCache = ACTIVE_CACHE_MANAGER.createCache(cacheAlias, activeCacheConfiguration);
    }

    @Override
    public void put(String value) {
        dataMap.put(index, value);
        if ((index + 1) % BATCH_COUNT == 0) {
            fileCache.put(index / BATCH_COUNT, dataMap);
            dataMap = new HashMap<Integer, String>(BATCH_COUNT * 4 / 3 + 1);
        }
        index++;
        if (LOGGER.isDebugEnabled()) {
            if (index % DEBUG_WRITE_SIZE == 0) {
                LOGGER.debug("Already put :{}", index);
            }
        }
    }

    @Override
    public String get(Integer key) {
        if (key == null || key < 0) {
            return null;
        }
        int route = key / BATCH_COUNT;
        HashMap<Integer, String> dataMap = activeCache.get(route);
        if (dataMap == null) {
            dataMap = fileCache.get(route);
            activeCache.put(route, dataMap);
            if (LOGGER.isDebugEnabled()) {
                if (cacheMiss++ % DEBUG_CACHE_MISS_SIZE == 0) {
                    LOGGER.debug("Cache misses count:{}", cacheMiss);
                }
            }
        }
        return dataMap.get(key);
    }

    @Override
    public void putFinished() {
        if (MapUtils.isEmpty(dataMap)) {
            return;
        }
        fileCache.put(index / BATCH_COUNT, dataMap);
    }

    @Override
    public void destroy() {
        FILE_CACHE_MANAGER.removeCache(cacheAlias);
        ACTIVE_CACHE_MANAGER.removeCache(cacheAlias);
    }

}
