package com.alibaba.easyexcel.test.temp.cache;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import com.alibaba.easyexcel.test.temp.poi.Poi2Test;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.fastjson2.JSON;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 **/
public class CacheTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Poi2Test.class);

    @Test
    public void cache() throws Exception {

        File readTempFile = FileUtils.createCacheTmpFile();

        File cacheFile = new File(readTempFile.getPath(), UUID.randomUUID().toString());
        PersistentCacheManager persistentCacheManager =
            CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerBuilder.persistence(cacheFile))
                .withCache("cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, HashMap.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB)))
                .build(true);
        Cache<Integer, HashMap> cache = persistentCacheManager.getCache("cache", Integer.class, HashMap.class);

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "test");

        cache.put(1, map);
        LOGGER.info("dd1:{}", JSON.toJSONString(cache.get(1)));

        cache.clear();

        LOGGER.info("dd2:{}", JSON.toJSONString(cache.get(1)));
    }

}
