package com.alibaba.excel.metadata;

import com.alibaba.excel.enums.HolderEnum;

/**
 *
 * Get the corresponding holder
 *
 * @author Jiaju Zhuang
 **/
public interface Holder {

    /**
     * What holder is the return
     *
     * @return Holder enum.
     */
    HolderEnum holderType();

}
