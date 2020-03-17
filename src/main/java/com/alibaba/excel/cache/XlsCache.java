package com.alibaba.excel.cache;

import org.apache.poi.hssf.record.SSTRecord;

import com.alibaba.excel.context.AnalysisContext;

/**
 *
 * Use SSTRecord.
 *
 * @author Jiaju Zhuang
 */
public class XlsCache implements ReadCache {
    private SSTRecord sstRecord;

    public XlsCache(SSTRecord sstRecord) {
        this.sstRecord = sstRecord;
    }

    @Override
    public void init(AnalysisContext analysisContext) {}

    @Override
    public void put(String value) {}

    @Override
    public String get(Integer key) {
        return sstRecord.getString(key).toString();
    }

    @Override
    public void putFinished() {}

    @Override
    public void destroy() {}

}
