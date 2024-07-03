package com.alibaba.easytools.spring.cache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.annotation.Resource;

import com.alibaba.easytools.spring.cache.wrapper.CacheWrapper;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * 缓存操作
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
public class EasyCache<V> {
    /**
     * 超时时间
     */
    private static final long DEFAULT_TIMEOUT = Duration.ofMinutes(2L).toMillis();
    /**
     * 同步锁的前缀
     */
    private static final String SYNCHRONIZED_PREFIX = "_EasyCache:";
    @Resource
    private HashOperations<String, String, CacheWrapper<V>> hashOperations;
    @Resource
    private ValueOperations<String, CacheWrapper<V>> valueOperations;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 去缓存里面获取一个值 并放入缓存
     *
     * @param key       缓存的key
     * @param queryData 查询数据
     * @return 缓存的值
     */
    public V get(String key, Supplier<V> queryData) {
        return get(key, queryData, DEFAULT_TIMEOUT);
    }

    /**
     * 去缓存里面获取一个值 并放入缓存
     *
     * @param key       缓存的key
     * @param queryData 查询数据
     * @param timeout   超时时长 ms
     * @return 缓存的值
     */
    public V get(String key, Supplier<V> queryData, Long timeout) {
        if (key == null) {
            return null;
        }
        // 先去缓存获取
        CacheWrapper<V> cacheWrapper = valueOperations.get(key);
        if (cacheWrapper != null) {
            return cacheWrapper.getData();
        }
        // 没有则锁住 然后第一个去获取
        String lockKey = SYNCHRONIZED_PREFIX + key;
        synchronized (lockKey.intern()) {
            // 重新获取
            cacheWrapper = valueOperations.get(key);
            if (cacheWrapper != null) {
                return cacheWrapper.getData();
            }

            // 真正的去查询数据
            V value = queryData.get();

            // 构建缓存
            CacheWrapper<V> cacheWrapperData = new CacheWrapper<>();
            cacheWrapperData.setData(value);
            valueOperations.set(key, cacheWrapperData, timeout, TimeUnit.MILLISECONDS);
            return value;
        }
    }

    /**
     * 去缓存里面获取一个值 并放入缓存
     *
     * @param key       缓存的key
     * @param hashKey   缓存的hashKey
     * @param queryData 查询数据
     * @return 缓存的值
     */
    public V hashGet(String key, String hashKey, Supplier<V> queryData) {
        if (key == null || hashKey == null) {
            return null;
        }
        // 先去缓存获取
        CacheWrapper<V> cacheWrapper = hashOperations.get(key, hashKey);
        if (cacheWrapper != null && System.currentTimeMillis() < cacheWrapper.getExpireTimeMillis()) {
            return cacheWrapper.getData();
        }

        // 没有则锁住 然后第一个去获取
        String lockKey = SYNCHRONIZED_PREFIX + key + ":" + hashKey;
        synchronized (lockKey.intern()) {
            // 重新获取
            cacheWrapper = hashOperations.get(key, hashKey);
            if (cacheWrapper != null && System.currentTimeMillis() < cacheWrapper.getExpireTimeMillis()) {
                return cacheWrapper.getData();
            }

            // 真正的去查询数据
            V value = queryData.get();

            // 构建缓存
            CacheWrapper<V> cacheWrapperData = new CacheWrapper<>();
            cacheWrapperData.setData(value);
            cacheWrapperData.setExpireTimeMillis(System.currentTimeMillis() + DEFAULT_TIMEOUT);
            hashOperations.put(key, hashKey, cacheWrapperData);
            stringRedisTemplate.boundHashOps(key).expire(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
            return value;
        }
    }

    /**
     * 移除缓存
     *
     * @param keys
     */
    public void delete(String... keys) {
        stringRedisTemplate.delete(Lists.newArrayList(keys));
    }

}
