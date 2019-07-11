package com.alibaba.excel.metadata.holder;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * 
 * Get the corresponding configuration
 * 
 * @author zhuangjiaju
 **/
public interface ConfigurationSelector {

    /**
     * What handler does the currently operated cell need to execute
     * 
     * @return
     */
    List<WriteHandler> writeHandlerList();

    /**
     * What converter does the currently operated cell need to execute
     * 
     * @return
     */
    Map<Class, Converter> converterMap();

    /**
     * Whether a header is required for the currently operated cell
     * 
     * @return
     */
    boolean needHead();

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     */
    int writeRelativeHeadRowIndex();

    /**
     * What 'ExcelHeadProperty' does the currently operated cell need to execute
     */
    ExcelHeadProperty excelHeadProperty();

    /**
     * 
     * Record whether it's new or from cache
     * 
     * @return
     */
    boolean isNew();
}
