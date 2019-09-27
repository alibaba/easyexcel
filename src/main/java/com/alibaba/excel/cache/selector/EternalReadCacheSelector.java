package com.alibaba.excel.cache.selector;

import org.apache.poi.openxml4j.opc.PackagePart;

import com.alibaba.excel.cache.ReadCache;

/**
 * Choose a eternal cache
 *
 * @author Jiaju Zhuang
 **/
public class EternalReadCacheSelector implements ReadCacheSelector {
    private ReadCache readCache;

    public EternalReadCacheSelector(ReadCache readCache) {
        this.readCache = readCache;
    }

    @Override
    public ReadCache readCache(PackagePart sharedStringsTablePackagePart) {
        return readCache;
    }
}
