package com.alibaba.excel.cache.selector;

import org.apache.poi.openxml4j.opc.PackagePart;

import com.alibaba.excel.cache.ReadCache;

/**
 * Select the cache
 *
 * @author Jiaju Zhuang
 **/
public interface ReadCacheSelector {

    /**
     * Select a cache
     *
     * @param sharedStringsTablePackagePart
     * @return
     */
    ReadCache readCache(PackagePart sharedStringsTablePackagePart);
}
