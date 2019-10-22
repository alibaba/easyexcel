package com.alibaba.easyexcel.test.demo.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 读取excel单元格的批注
 *
 * @uthor: likai.yu
 * @ate: 2019-10-22 16:25
 **/
public class CellDataDemoCommentListener extends AnalysisEventListener<CellDataReadDemoData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CellDataDemoCommentListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<CellDataReadDemoData> list = new ArrayList<CellDataReadDemoData>();

    @Override
    public void invoke(CellDataReadDemoData data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        if (null != data.getFormulaValue() && null != data.getFormulaValue().getComment()) {
            LOGGER.info("数据{}, formulaValue字段的注释:{}", JSON.toJSONString(data), data.getFormulaValue().getComment());
        }
        if (null != data.getDate() && null != data.getDate().getComment()) {
            LOGGER.info("数据{}, date:{}", JSON.toJSONString(data), data.getDate().getComment());
        }
        if (null != data.getDoubleData() && null != data.getDoubleData().getComment()) {
            LOGGER.info("数据{}, doubleData字段的注释:{}", JSON.toJSONString(data), data.getDoubleData().getComment());
        }
        if (null != data.getString() && null != data.getString().getComment()) {
            LOGGER.info("数据{}, string字段的注释:{}", JSON.toJSONString(data), data.getString().getComment());
        }
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        LOGGER.info("存储数据库成功！");
    }
}
