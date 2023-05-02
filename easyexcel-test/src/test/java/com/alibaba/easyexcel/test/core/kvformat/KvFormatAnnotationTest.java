package com.alibaba.easyexcel.test.core.kvformat;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiajiafu
 * @since 2022/7/14
 */
public class KvFormatAnnotationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KvFormatAnnotationTest.class);

    @Test
    public void doWrite() throws Exception {
        File myFile = TestFileUtil.createNewFile("key_value_write.xlsx");
        EasyExcel.write().file(myFile).head(KeyValueData.class).sheet()
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(data());
    }

    @Test
    public void read() throws Exception {
        List<KeyValueData> list = new ArrayList<>();
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "keyValueRead.xlsx";
        EasyExcel.read(fileName).head(KeyValueData.class).sheet(0)
            .registerReadListener(new AnalysisEventListener<KeyValueData>() {
                @Override
                public void invoke(KeyValueData data, AnalysisContext context) {
                    if (data != null) {
                        list.add(data);
                    }
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    LOGGER.info("解析完毕,共解析{}条数据", list.size());
                }
            }).autoTrim(true)
            .doRead();
        list.forEach(o -> System.out.println(JSON.toJSONString(o)));
        Assert.assertEquals(list.get(0).getOuType(), DepartmentEnum.DEPT_1.getKey());
    }

    private List<KeyValueData> data() {
        List<KeyValueData> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            KeyValueData data = new KeyValueData();
            data.setName("员工" + i);
            data.setOuType(i % 2 + 1);
            if (i % 2 == 0) {
                data.setSexType("male");
            } else {
                data.setSexType("female");
            }
            list.add(data);
        }
        return list;
    }

}
