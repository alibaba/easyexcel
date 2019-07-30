package com.alibaba.excel.metadata;

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

}
