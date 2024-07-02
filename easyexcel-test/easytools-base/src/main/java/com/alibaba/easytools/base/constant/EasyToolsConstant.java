package com.alibaba.easytools.base.constant;

/**
 * 常量
 *
 * @author 是仪
 */
public interface EasyToolsConstant {

    /**
     * 最大分页大小
     */
    int MAX_PAGE_SIZE = 500;

    /**
     * 序列化id
     */
    long SERIAL_VERSION_UID = 1L;

    /**
     * 最大循环次数 防止很多循环进入死循环
     */
    int MAXIMUM_ITERATIONS = 10 * 1000;

    /**
     * 缓存占位符
     * 有些缓存只要判断有没有，可以放入这个
     */
    String CACHE_PLACEHOLDER = "C";

    /**
     * 鹰眼追踪id
     */
    String EAGLEEYE_TRACE_ID = "EAGLEEYE_TRACE_ID";

}
