package com.alibaba.excel.read.metadata.holder;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ConverterKey;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;

/**
 * 
 * Get the corresponding Holder
 * 
 * @author zhuangjiaju
 **/
public interface ReadHolder extends Holder {
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
    Map<ConverterKey, Converter> converterMap();

    /**
     * What 'ExcelReadHeadProperty' does the currently operated cell need to execute
     * 
     * @return
     */
    ExcelReadHeadProperty excelReadHeadProperty();

}
