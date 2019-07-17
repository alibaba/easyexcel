package com.alibaba.easyexcel.test.ehcache;

import java.io.File;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.util.POITempFile;

/**
 * @author zhuangjiaju
 */
@Ignore
public class EncacheTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncacheTest.class);

    @Test
    public void cache() {
        File file = POITempFile.createCacheTmpFile();
        PersistentCacheManager persistentCacheManager =
            CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerBuilder.persistence(file))
                .withCache("cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB)))
                .build(true);
        Cache<Integer, String> cache = persistentCacheManager.getCache("cache", Integer.class, String.class);
        cache.put(1, "测试1");
        LOGGER.info("cache:{}", cache.get(1));
        persistentCacheManager.close();
        LOGGER.info("close");
        POITempFile.delete(file);
    }
}
