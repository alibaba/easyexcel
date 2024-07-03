package com.alibaba.easytools.spring.cache.wrapper;

import java.io.Serializable;

import lombok.Data;

/**
 * 缓存包装
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
@Data
public class CacheWrapper<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 超时时间戳
     */
    private Long expireTimeMillis;

    /**
     * 数据
     */
    private T data;
}
