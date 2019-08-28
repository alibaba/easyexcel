package com.alibaba.excel.cache;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.StringUtils;

/**
 * Default cache
 *
 * @author Jiaju Zhuang
 */
public class Ehcache implements ReadCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ehcache.class);

    private static final int BATCH_COUNT = 1000;
    private static final int CHECK_INTERVAL = 500;
    private static final int MAX_CACHE_ACTIVATE = 10;

    private static final String CACHE = "cache";
    private static final String DATA_SEPARATOR = "@";
    private static final String KEY_VALUE_SEPARATOR = "!";
    private static final String SPECIAL_SEPARATOR = "&";
    private static final String ESCAPED_DATA_SEPARATOR = "&d;";
    private static final String ESCAPED_KEY_VALUE_SEPARATOR = "&kv;";
    private static final String ESCAPED_SPECIAL_SEPARATOR = "&s;";

    private static final int DEBUG_WRITE_SIZE = 100 * 10000;
    private static final int DEBUG_CACHE_MISS_SIZE = 1000;

    /**
     * Key index
     */
    private int index = 0;
    private StringBuilder data = new StringBuilder();
    private CacheManager cacheManager;
    /**
     * Bulk storage data
     */
    private org.ehcache.Cache<Integer, String> cache;
    /**
     * Currently active cache
     */
    private Map<Integer, Map<Integer, String>> cacheMap = new HashMap<Integer, Map<Integer, String>>();
    /**
     * Count how many times get
     */
    private int getCount = 0;
    /**
     * Count active cache
     *
     */
    private LinkedList<Integer> countList = new LinkedList<Integer>();

    /**
     * Count the last {@link #CHECK_INTERVAL} used
     */
    private Set<Integer> lastCheckIntervalUsedSet = new HashSet<Integer>();

    /**
     * Count the number of cache misses
     */
    private int cacheMiss = 0;

    @Override
    public void init(AnalysisContext analysisContext) {
        File readTempFile = analysisContext.readWorkbookHolder().getTempFile();
        if (readTempFile == null) {
            readTempFile = FileUtils.createCacheTmpFile();
            analysisContext.readWorkbookHolder().setTempFile(readTempFile);
        }
        File cacheFile = new File(readTempFile.getPath(), UUID.randomUUID().toString());
        PersistentCacheManager persistentCacheManager =
            CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerBuilder.persistence(cacheFile))
                .withCache(CACHE, CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                    ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB)))
                .build(true);
        cacheManager = persistentCacheManager;
        cache = persistentCacheManager.getCache(CACHE, Integer.class, String.class);
    }

    @Override
    public void put(String value) {
        data.append(index).append(KEY_VALUE_SEPARATOR).append(escape(value)).append(DATA_SEPARATOR);
        if ((index + 1) % BATCH_COUNT == 0) {
            cache.put(index / BATCH_COUNT, data.toString());
            data = new StringBuilder();
        }
        index++;
        if (LOGGER.isDebugEnabled()) {
            if (index % DEBUG_WRITE_SIZE == 0) {
                LOGGER.debug("Already put :{}", index);
            }
        }
    }

    private String escape(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        str = str.replaceAll(SPECIAL_SEPARATOR, ESCAPED_SPECIAL_SEPARATOR);
        str = str.replaceAll(DATA_SEPARATOR, ESCAPED_DATA_SEPARATOR);
        str = str.replaceAll(KEY_VALUE_SEPARATOR, ESCAPED_KEY_VALUE_SEPARATOR);
        return str;
    }

    private String unescape(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        str = str.replaceAll(ESCAPED_KEY_VALUE_SEPARATOR, KEY_VALUE_SEPARATOR);
        str = str.replaceAll(ESCAPED_DATA_SEPARATOR, DATA_SEPARATOR);
        str = str.replaceAll(ESCAPED_SPECIAL_SEPARATOR, SPECIAL_SEPARATOR);
        return str;
    }

    @Override
    public String get(Integer key) {
        if (key == null || key < 0) {
            return null;
        }
        getCount++;
        int route = key / BATCH_COUNT;
        if (cacheMap.containsKey(route)) {
            lastCheckIntervalUsedSet.add(route);
            String value = cacheMap.get(route).get(key);
            checkClear();
            return value;
        }
        Map<Integer, String> tempCacheMap = new HashMap<Integer, String>(BATCH_COUNT / 3 * 4 + 1);
        String batchData = cache.get(route);
        String[] dataStrings = batchData.split(DATA_SEPARATOR);
        for (String dataString : dataStrings) {
            String[] keyValue = dataString.split(KEY_VALUE_SEPARATOR);
            tempCacheMap.put(Integer.valueOf(keyValue[0]), unescape(keyValue[1]));
        }
        countList.add(route);
        cacheMap.put(route, tempCacheMap);
        if (LOGGER.isDebugEnabled()) {
            if (cacheMiss++ % DEBUG_CACHE_MISS_SIZE == 0) {
                LOGGER.debug("Cache misses count:{}", cacheMiss);
            }
        }
        lastCheckIntervalUsedSet.add(route);
        String value = tempCacheMap.get(key);
        checkClear();
        return value;
    }

    private void checkClear() {
        if (countList.size() > MAX_CACHE_ACTIVATE) {
            Integer route = countList.getFirst();
            countList.removeFirst();
            cacheMap.remove(route);
        }
        if (getCount++ % CHECK_INTERVAL != 0) {
            return;
        }
        Iterator<Map.Entry<Integer, Map<Integer, String>>> iterator = cacheMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Map<Integer, String>> entry = iterator.next();
            if (lastCheckIntervalUsedSet.contains(entry.getKey())) {
                continue;
            }
            // Last 'CHECK_INTERVAL' not use
            iterator.remove();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Cache remove because {} times unused.", CHECK_INTERVAL);
            }
            Iterator<Integer> countIterator = countList.iterator();
            while (countIterator.hasNext()) {
                Integer route = countIterator.next();
                if (route.equals(entry.getKey())) {
                    countIterator.remove();
                    break;
                }
            }
        }
        lastCheckIntervalUsedSet.clear();
    }

    @Override
    public void putFinished() {
        if (StringUtils.isEmpty(data.toString())) {
            return;
        }
        cache.put(index / BATCH_COUNT, data.toString());
    }

    @Override
    public void destroy() {
        cacheManager.close();
    }

}
