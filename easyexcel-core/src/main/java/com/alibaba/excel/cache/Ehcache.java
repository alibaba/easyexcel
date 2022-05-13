package com.alibaba.excel.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

/**
 * Default cache
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class Ehcache implements ReadCache {
    public static final int BATCH_COUNT = 100;
    /**
     * Key index
     */
    private int activeIndex = 0;
    public static final int DEBUG_CACHE_MISS_SIZE = 1000;
    public static final int DEBUG_WRITE_SIZE = 100 * 10000;
    private ArrayList<String> dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private static final CacheManager FILE_CACHE_MANAGER;
    private static final CacheConfiguration<Integer, ArrayList> FILE_CACHE_CONFIGURATION;
    private static final CacheManager ACTIVE_CACHE_MANAGER;
    private final CacheConfiguration<Integer, ArrayList> activeCacheConfiguration;
    /**
     * Bulk storage data
     */
    private org.ehcache.Cache<Integer, ArrayList> fileCache;
    /**
     * Currently active cache
     */
    private org.ehcache.Cache<Integer, ArrayList> activeCache;
    private String cacheAlias;
    /**
     * Count the number of cache misses
     */
    private int cacheMiss = 0;

    public Ehcache(int maxCacheActivateSize) {
        activeCacheConfiguration = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(Integer.class, ArrayList.class,
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
            .newCacheConfigurationBuilder(Integer.class, ArrayList.class,
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
        dataList.add(value);
        if (dataList.size() >= BATCH_COUNT) {
            fileCache.put(activeIndex, dataList);
            activeIndex++;
            dataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
        if (log.isDebugEnabled()) {
            int alreadyPut = activeIndex * BATCH_COUNT + dataList.size();
            if (alreadyPut % DEBUG_WRITE_SIZE == 0) {
                log.debug("Already put :{}", alreadyPut);
            }
        }
    }

    @Override
    public String get(Integer key) {
        if (key == null || key < 0) {
            return null;
        }
        int route = key / BATCH_COUNT;
        ArrayList<String> dataList = activeCache.get(route);
        if (dataList == null) {
            dataList = fileCache.get(route);
            activeCache.put(route, dataList);
            if (log.isDebugEnabled()) {
                if (cacheMiss++ % DEBUG_CACHE_MISS_SIZE == 0) {
                    log.debug("Cache misses count:{}", cacheMiss);
                }
            }
        }
        return dataList.get(key % BATCH_COUNT);
    }

    @Override
    public void putFinished() {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        fileCache.put(activeIndex, dataList);
    }

    @Override
    public void destroy() {
        FILE_CACHE_MANAGER.removeCache(cacheAlias);
        ACTIVE_CACHE_MANAGER.removeCache(cacheAlias);
    }

}
