package com.alibaba.excel.event;

/**
 * There are multiple interceptors that execute only one of them when fired once.If you want to control which one to
 * execute please use {@link Order}
 *
 * @author Jiaju Zhuang
 **/
public interface NotRepeatExecutor {
    /**
     * To see if it's the same executor
     *
     * @return
     */
    String uniqueValue();
}
