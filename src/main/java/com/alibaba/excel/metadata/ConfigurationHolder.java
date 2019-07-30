package com.alibaba.excel.metadata;

import java.util.Map;

import com.alibaba.excel.converters.Converter;

/**
 *
 * Get the corresponding holder
 *
 * @author zhuangjiaju
 **/
public interface ConfigurationHolder extends Holder {

    /**
     *
     * Record whether it's new or from cache
     *
     * @return
     */
    boolean isNew();

    /**
     * Some global variables
     *
     * @return
     */
    GlobalConfiguration globalConfiguration();

    /**
     * What converter does the currently operated cell need to execute
     *
     * @return
     */
    Map<String, Converter> converterMap();
}
