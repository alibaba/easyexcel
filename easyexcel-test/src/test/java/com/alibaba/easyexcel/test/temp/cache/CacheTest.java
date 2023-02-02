package com.alibaba.easyexcel.test.temp.cache;

import java.io.File;
import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.UUID;

import com.alibaba.excel.cache.Ehcache;
import com.alibaba.excel.cache.ReadCache;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.context.xlsx.DefaultXlsxReadContext;
import com.alibaba.excel.read.metadata.ReadWorkbook;
import com.alibaba.excel.support.ExcelTypeEnum;
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

import com.alibaba.easyexcel.test.temp.poi.Poi2Test;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.fastjson.JSON;

/**
 * @author Jiaju Zhuang
 **/
@Ignore
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

    @Test
    public void cacheClean() throws Exception{
        ReadCache readCache = new Ehcache(10);
        readCache.put("1");
        Field field = readCache.getClass().getDeclaredField("CACHE_PATH");
        field.setAccessible(true);
        File cachePath = (File) field.get(readCache);
        if (cachePath.exists()) {
            FileUtils.delete(cachePath.getParentFile().getParentFile());
        }
        AnalysisContext context = new DefaultXlsxReadContext(new ReadWorkbook(), ExcelTypeEnum.XLSX);
        readCache.init(context);
        readCache.put("ddddd");
    }

}
