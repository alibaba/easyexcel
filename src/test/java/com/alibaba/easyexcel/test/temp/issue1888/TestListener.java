package com.alibaba.easyexcel.test.temp.issue1888;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class TestListener extends AnalysisEventListener<TestData> {

    private static final int BATCH_COUNT = 6;

    List<TestData> list = new ArrayList<TestData>();
    @Override
    public void invoke(TestData data, AnalysisContext context) {
        System.out.println("解析到一条数据:" + JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("最后一次解析了" + list.size() + "条数据！");
        System.out.println("所有数据解析完成！");
    }
}
