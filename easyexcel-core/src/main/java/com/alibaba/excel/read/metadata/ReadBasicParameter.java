package com.alibaba.excel.read.metadata;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.metadata.BasicParameter;
import com.alibaba.excel.read.listener.ReadListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Read basic parameter
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class ReadBasicParameter extends BasicParameter {
    /**
     * Count the number of added heads when read sheet.
     *
     * <p>
     * 0 - This Sheet has no head ,since the first row are the data
     * <p>
     * 1 - This Sheet has one row head , this is the default
     * <p>
     * 2 - This Sheet has two row head ,since the third row is the data
     */
    private Integer headRowNumber;
    /**
     * Custom type listener run after default
     */
    private List<ReadListener<?>> customReadListenerList;

    public ReadBasicParameter() {
        customReadListenerList = new ArrayList<>();
    }
}
