package com.alibaba.excel.metadata.holder.write;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.property.ExcelHeadProperty;
import com.alibaba.excel.write.handler.WriteHandler;

/**
 * 
 * Get the corresponding configuration
 * 
 * @author zhuangjiaju
 **/
public interface WriteConfiguration {

    /**
     * What handler does the currently operated cell need to execute
     * 
     * @return
     */
    Map<Class<? extends WriteHandler>, List<WriteHandler>> writeHandlerMap();

    /**
     * What converter does the currently operated cell need to execute
     * 
     * @return
     */
    Map<Class, Converter> writeConverterMap();

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
