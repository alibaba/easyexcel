package com.alibaba.easytools.common.util.function;

/**
 * 没有入参 出参的function
 */
@FunctionalInterface
public interface Executor {

    /**
     * 执行
     */
    void execute();
}
