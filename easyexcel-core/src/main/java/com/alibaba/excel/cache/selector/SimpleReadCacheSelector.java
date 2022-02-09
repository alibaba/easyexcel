package com.alibaba.excel.cache.selector;

import java.io.IOException;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.cache.Ehcache;
import com.alibaba.excel.cache.MapCache;
import com.alibaba.excel.cache.ReadCache;

/**
 * Simple cache selector
 *
 * @author Jiaju Zhuang
 **/
public class SimpleReadCacheSelector implements ReadCacheSelector {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReadCacheSelector.class);
    /**
     * Convert bytes to megabytes
     */
    private static final long B2M = 1000 * 1000L;
    /**
     * If it's less than 5M, use map cache, or use ehcache.unit MB.
     */
    private static final int DEFAULT_MAX_USE_MAP_CACHE_SIZE = 5;
    /**
     * Maximum size of cache activation.unit MB.
     */
    private static final int DEFAULT_MAX_EHCACHE_ACTIVATE_SIZE = 20;

    /**
     * Shared strings exceeding this value will use {@link Ehcache},or use {@link MapCache}.unit MB.
     */
    private long maxUseMapCacheSize;

    /**
     * Maximum size of cache activation.unit MB.
     */
    private int maxCacheActivateSize;

    public SimpleReadCacheSelector() {
        this(DEFAULT_MAX_USE_MAP_CACHE_SIZE, DEFAULT_MAX_EHCACHE_ACTIVATE_SIZE);
    }

    public SimpleReadCacheSelector(long maxUseMapCacheSize, int maxCacheActivateSize) {
        if (maxUseMapCacheSize <= 0) {
            this.maxUseMapCacheSize = DEFAULT_MAX_USE_MAP_CACHE_SIZE;
        } else {
            this.maxUseMapCacheSize = maxUseMapCacheSize;
        }
        if (maxCacheActivateSize <= 0) {
            this.maxCacheActivateSize = DEFAULT_MAX_EHCACHE_ACTIVATE_SIZE;
        } else {
            this.maxCacheActivateSize = maxCacheActivateSize;
        }
    }

    @Override
    public ReadCache readCache(PackagePart sharedStringsTablePackagePart) {
        long size = sharedStringsTablePackagePart.getSize();
        if (size < 0) {
            try {
                size = sharedStringsTablePackagePart.getInputStream().available();
            } catch (IOException e) {
                LOGGER.warn("Unable to get file size, default used MapCache");
                return new MapCache();
            }
        }
        if (size < maxUseMapCacheSize * B2M) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Use map cache.size:{}", size);
            }
            return new MapCache();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Use ehcache.size:{}", size);
        }
        return new Ehcache(maxCacheActivateSize);
    }
}
