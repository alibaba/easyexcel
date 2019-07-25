package com.alibaba.excel.metadata;

import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.HolderEnum;

/**
 * 
 * Get the corresponding holder
 * 
 * @author zhuangjiaju
 **/
public interface Holder {

    /**
     * What holder is the return
     * 
     * @return
     */
    HolderEnum holderType();

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
