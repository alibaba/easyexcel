package com.alibaba.excel.read.metadata.holder;

import java.util.List;

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
     * What 'ExcelReadHeadProperty' does the currently operated cell need to execute
     * 
     * @return
     */
    ExcelReadHeadProperty excelReadHeadProperty();

}
