package com.alibaba.easyexcel.test.temp.read;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
public class HeadListener extends AnalysisEventListener<HeadReadData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeadListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        LOGGER.info("HEAD:{}", JSON.toJSONString(headMap));
        LOGGER.info("total:{}", context.readSheetHolder().getTotal());

    }

    @Override
    public void invoke(HeadReadData data, AnalysisContext context) {
        LOGGER.info("index:{}", context.readRowHolder().getRowIndex());
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
    }

}
