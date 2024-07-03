package com.alibaba.easytools.spring.cache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.annotation.Resource;

import com.alibaba.easytools.base.excption.BusinessException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.support.NullValue;

/**
 * 缓存服务v2
 *
 * @author 是仪
 */
@Slf4j
public class EasyCacheV2 {
    /**
     * 超时时间
     */
    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(2L);
    /**
     * 同步锁的前缀
     */
    private static final String SYNCHRONIZED_PREFIX = "_EasyCacheV2:";

    @Resource
    private RedissonClient redissonClient;

    /**
     * 去缓存里面获取一个值 并放入缓存
     *
     * @param key       缓存的key
     * @param queryData 查询数据
     * @return 缓存的值
     */
    public <T> T computeIfAbsent(String key, Supplier<T> queryData) {
        return computeIfAbsent(key, queryData, DEFAULT_TIMEOUT);
    }

    /**
     * 去缓存里面获取一个值 并放入缓存
     *
     * @param key       缓存的key
     * @param queryData 查询数据
     * @param duration  超时时长
     * @return 缓存的值
     */
    public <T> T computeIfAbsent(String key, Supplier<T> queryData, Duration duration) {
        if (key == null) {
            return null;
        }
        // 先去缓存获取
        T data = get(key);
        if (data != null) {
            return data;
        }
        // 没有则锁住 然后第一个去获取
        String lockKey = SYNCHRONIZED_PREFIX + key;
        synchronized (lockKey.intern()) {
            // 重新获取
            data = get(key);
            if (data != null) {
                return data;
            }

            // 真正的去查询数据
            T value = queryData.get();

            // 构建缓存
            set(key, value, duration);
            return value;
        }
    }

    /**
     * 移除缓存
     *
     * @param keys
     */
    public void delete(String... keys) {
        redissonClient.getKeys().delete(keys);
    }

    /**
     * 同步执行任务,等待时间为一分钟，执行释放锁为一分钟，推荐mq回调执行简单任务使用
     * 在等待超时后抛出异常
     *
     * @param lockKey  锁 全局唯一
     * @param runnable 同步执行的任务
     */
    public void synchronousExecuteOneMinuteAndThrowException(String lockKey, Runnable runnable) {
        synchronousExecute(lockKey, runnable, () -> {
            log.warn("经过60秒没有抢到锁");
            throw BusinessException.of("经过60秒没有抢到锁");
        }, Duration.ofMinutes(1L), Duration.ofMinutes(1L));

    }

    /**
     * 同步执行任务,等待时间为一分钟，执行释放锁为一分钟，推荐mq回调执行简单任务使用
     * 在等待超时后抛出异常
     *
     * @param lockKey  锁 全局唯一
     * @param runnable 同步执行的任务
     */
    public void synchronousExecuteThrowException(String lockKey, Runnable runnable, Duration waitDuration,
        Duration leaseDuration) {
        synchronousExecute(lockKey, runnable, () -> {
            log.warn("经过{}秒没有抢到锁", waitDuration.getSeconds());
            throw BusinessException.of("经过" + waitDuration.getSeconds() + "秒没有抢到锁");
        }, waitDuration, leaseDuration);

    }

    /**
     * 同步执行任务,等待时间为一分钟，执行释放锁为一分钟，推荐mq回调执行简单任务使用
     * 在等待超时后抛出异常
     *
     * @param lockKey  锁 全局唯一
     * @param supplier 同步执行的任务
     */
    public <T> T synchronousExecuteOneMinuteAndThrowException(String lockKey, Supplier<T> supplier) {
        return synchronousExecute(lockKey, supplier, () -> {
            log.warn("经过60秒没有抢到锁");
            throw BusinessException.of("经过60秒没有抢到锁");
        }, Duration.ofMinutes(1L), Duration.ofMinutes(1L));
    }

    /**
     * 同步执行任务,等待时间为一分钟，执行释放锁为一分钟，推荐mq回调执行简单任务使用
     * 在等待超时后抛出异常
     *
     * @param lockKey       锁 全局唯一
     * @param supplier      同步执行的任务
     * @param waitDuration  等待超时时间
     * @param leaseDuration 执行任务多少时间后释放锁
     */
    public <T> T synchronousExecuteThrowException(String lockKey, Supplier<T> supplier,
        Duration waitDuration, Duration leaseDuration) {
        return synchronousExecute(lockKey, supplier, () -> {
            log.warn("经过{}秒没有抢到锁", waitDuration.getSeconds());
            throw BusinessException.of("经过" + waitDuration.getSeconds() + "秒没有抢到锁");
        }, waitDuration, leaseDuration);
    }

    /**
     * 同步执行任务,等待0秒后立即执行waitTimeOutExecutor
     *
     * @param lockKey             锁 全局唯一
     * @param runnable            同步执行的任务
     * @param waitTimeOutRunnable 等待超时以后的处理逻辑
     * @param leaseDuration       执行任务多少时间后释放锁
     */
    public void synchronousExecuteFailFast(String lockKey, Runnable runnable, Runnable waitTimeOutRunnable,
        Duration leaseDuration) {
        synchronousExecute(lockKey, runnable, waitTimeOutRunnable, Duration.ZERO, leaseDuration);
    }

    /**
     * 同步执行任务
     *
     * @param lockKey             锁 全局唯一
     * @param runnable            同步执行的任务
     * @param waitTimeOutRunnable 等待超时以后的处理逻辑
     * @param waitDuration        等待超时时间
     * @param leaseDuration       执行任务多少时间后释放锁
     */
    public void synchronousExecute(String lockKey, Runnable runnable, Runnable waitTimeOutRunnable,
        Duration waitDuration, Duration leaseDuration) {
        //同一对象 不能并发
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            // 尝试加锁 最多等待 waitTime , 并且在leaseTime 之后释放锁
            if (rLock.tryLock(waitDuration.getSeconds(), leaseDuration.getSeconds(), TimeUnit.SECONDS)) {
                runnable.run();
            } else {
                if (waitTimeOutRunnable != null) {
                    waitTimeOutRunnable.run();
                }
            }
        } catch (InterruptedException e) {
            log.error("执行任务被打断:{}", lockKey, e);
            if (waitTimeOutRunnable != null) {
                waitTimeOutRunnable.run();
            }
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }
    }

    /**
     * 同步执行任务
     *
     * @param lockKey             锁 全局唯一
     * @param supplier            同步执行的任务
     * @param waitTimeOutRunnable 等待超时以后的处理逻辑
     * @param waitDuration        等待超时时间
     * @param leaseDuration       执行任务多少时间后释放锁
     */
    public <T> T synchronousExecute(String lockKey, Supplier<T> supplier, Runnable waitTimeOutRunnable,
        Duration waitDuration, Duration leaseDuration) {
        //同一对象 不能并发
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            // 尝试加锁 最多等待 waitTime , 并且在leaseTime 之后释放锁
            if (rLock.tryLock(waitDuration.getSeconds(), leaseDuration.getSeconds(), TimeUnit.SECONDS)) {
                return supplier.get();
            } else {
                if (waitTimeOutRunnable != null) {
                    waitTimeOutRunnable.run();
                }
                return null;
            }
        } catch (InterruptedException e) {
            log.error("执行任务被打断:{}", lockKey, e);
            if (waitTimeOutRunnable != null) {
                waitTimeOutRunnable.run();
            }
            return null;
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }
    }

    /**
     * 查询操作
     *
     * @param key 缓存key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        RBucket<T> rBucket = redissonClient.getBucket(key);
        T data = rBucket.get();
        // 在存储空的数据的时候 存储了一个空的对象
        if (data instanceof NullValue) {
            return null;
        }
        return data;
    }

    /**
     * 设置一个值
     *
     * @param key
     * @param value
     * @param duration
     */
    public void set(String key, Object value, Duration duration) {
        Object cacheValue = value;
        if (cacheValue == null) {
            cacheValue = NullValue.INSTANCE;
        }
        if (duration == null) {
            duration = DEFAULT_TIMEOUT;
        }
        redissonClient.getBucket(key)
            // 为了兼容老版本
            .set(cacheValue, duration.getSeconds(), TimeUnit.SECONDS);
    }
}
