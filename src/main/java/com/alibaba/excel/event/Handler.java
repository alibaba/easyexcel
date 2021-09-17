package com.alibaba.excel.event;

import com.alibaba.excel.constant.OrderConstant;

/**
 * Intercepts handle some business logic
 *
 * @author Jiaju Zhuang
 **/
public interface Handler extends Order {

    /**
     * handler order
     *
     * @return order
     */
    @Override
    default int order() {
        return OrderConstant.DEFAULT_ORDER;
    }
}
