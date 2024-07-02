package com.alibaba.easytools.base.handler;

/**
 * 回调的处理器
 * 比如meatq回调的时候 会执行这个方法
 *
 * @author 是仪
 */
public interface EasyCallBackHandler {
    /**
     * 在处理回调前调用
     */
    default void preHandle() {
    }

    /**
     * 在处理回调后调用
     * 抛出异常了不会处理
     */
    default void postHandle() {
    }

    /**
     * 在处理回调后调用
     * 无论是否异常 都会调用
     */
    default void afterCompletion() {
    }
}
