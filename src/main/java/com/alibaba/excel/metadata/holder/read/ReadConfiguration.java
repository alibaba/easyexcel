package com.alibaba.excel.metadata.holder.read;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.read.listener.ReadListener;

/**
 * 
 * Get the corresponding configuration
 * 
 * @author zhuangjiaju
 **/
public interface ReadConfiguration {
    /**
     * What handler does the currently operated cell need to execute
     * 
     * @return
     */
    List<ReadListener> readListenerList();

    /**
     * What converter does the currently operated cell need to execute
     * 
     * @return
     */
    Map<ConverterKey, Converter> readConverterMap();

    /**
     * What 'ExcelHeadProperty' does the currently operated cell need to execute
     * 
     * @return
     */
    ExcelHeadProperty excelHeadProperty();

}
