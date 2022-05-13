package com.alibaba.excel.metadata;

import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKeyBuild.ConverterKey;

/**
 * Get the corresponding holder
 *
 * @author Jiaju Zhuang
 **/
public interface ConfigurationHolder extends Holder {

    /**
     * Record whether it's new or from cache
     *
     * @return Record whether it's new or from cache
     */
    boolean isNew();

    /**
     * Some global variables
     *
     * @return Global configuration
     */
    GlobalConfiguration globalConfiguration();

    /**
     * What converter does the currently operated cell need to execute
     *
     * @return Converter
     */
    Map<ConverterKey, Converter<?>> converterMap();
}
