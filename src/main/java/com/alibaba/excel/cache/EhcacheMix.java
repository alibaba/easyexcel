package com.alibaba.excel.cache;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.util.POITempFile;
import com.alibaba.excel.util.StringUtils;

/**
 * Default cache
 * 
 * @author zhuangjiaju
 */
public class EhcacheMix implements Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(EhcacheMix.class);
    private static final int BATCH = 500;
    int index = 0;
    int expirekey = 0;
    private org.ehcache.Cache<Integer, String> cache;
    private Map<Integer, Map<Integer, String>> cacheMap = new HashMap<Integer, Map<Integer, String>>();
    private TreeMap<Integer, Integer> expire = new TreeMap<Integer, Integer>();
    private StringBuilder sb = new StringBuilder();
    private Set<Integer> count = new HashSet<Integer>();
    private int getCount = 1;

    private int countRead = 0;

    public EhcacheMix() {
        File file = POITempFile.createCacheTmpFile();
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .with(CacheManagerBuilder.persistence(file))
            .withCache("cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(20, MemoryUnit.MB).disk(2, MemoryUnit.GB, false)))
            .build(true);
        this.cache = persistentCacheManager.getCache("cache", Integer.class, String.class);
    }

    @Override
    public void put(Integer key, String value) {
        sb.append(index++).append(":").append(value).append(",");
        if (index != 0 && index % BATCH == 0) {
            int key1 = index / BATCH;
            cache.put(key1, sb.toString());
            // LOGGER.info("put:key:{},{}", key1, sb.toString());
            sb = new StringBuilder();
        }
    }

    @Override
    public String get(Integer key) {
        int route = key / BATCH;
        route++;
        if (!cacheMap.containsKey(route)) {
            Map<Integer, String> map = new HashMap<Integer, String>(10000 / 3 * 4 + 1);
            String s = cache.get(route);
            String[] values = s.split(",");
            for (String value : values) {
                String[] vv = value.split(":");
                map.put(Integer.valueOf(vv[0]), vv[1]);
            }
            cacheMap.put(route, map);
            expire.put(expirekey++, route);
        }
        count.add(route);

        if (getCount++ % 1000 == 0) {
            Iterator<Map.Entry<Integer, Map<Integer, String>>> iterator = cacheMap.entrySet().iterator();
            // LOGGER.info("size:{}", cacheMap.size());
            while (iterator.hasNext()) {
                Map.Entry<Integer, Map<Integer, String>> entry = iterator.next();
                if (!count.contains(entry.getKey())) {
                    // LOGGER.info("route:{},remove", entry.getKey());
                    iterator.remove();
                    Iterator<Map.Entry<Integer, Integer>> ex = expire.entrySet().iterator();
                    while (ex.hasNext()) {
                        Map.Entry<Integer, Integer> e = ex.next();
                        if (e.getValue().equals(entry.getKey())) {
                            ex.remove();
                            break;
                        }
                    }
                }
            }
            count.clear();
        }

        if (expire.size() > 10) {
            int value1 = expire.firstEntry().getValue();
            int key1 = expire.firstEntry().getKey();
            cacheMap.remove(value1);
            expire.remove(key1);
        }

        return cacheMap.get(route).get(key);
    }

    @Override
    public void finish() {
        if (StringUtils.isEmpty(sb.toString())) {
            return;
        }
        int key1 = index / BATCH;
        if (index % BATCH != 0) {
            key1++;
        }

        cache.put(key1, sb.toString());
        // LOGGER.info("put:key:{},{}", key1 + 1, sb.toString());
    }
}
