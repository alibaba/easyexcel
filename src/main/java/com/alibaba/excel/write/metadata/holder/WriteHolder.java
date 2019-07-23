package com.alibaba.excel.write.metadata.holder;

import java.util.List;
import java.util.Map;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.property.ExcelWriteHeadProperty;

/**
 * 
 * Get the corresponding Holder
 * 
 * @author zhuangjiaju
 **/
public interface WriteHolder extends Holder {
    /**
     * What 'ExcelWriteHeadProperty' does the currently operated cell need to execute
     */
    ExcelWriteHeadProperty excelWriteHeadProperty();

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
    Map<Class, Converter> converterMap();

    /**
     * Whether a header is required for the currently operated cell
     * 
     * @return
     */
    boolean needHead();

    /**
     * Writes the head relative to the existing contents of the sheet. Indexes are zero-based.
     * 
     * @return
     */
    int relativeHeadRowIndex();
}
