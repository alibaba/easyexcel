package com.alibaba.easyexcel.test.temp.write;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.easyexcel.test.demo.read.CustomStringStringConverter;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ListUtils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.cglib.beans.BeanMap;

@Ignore
@Slf4j
public class TempWriteTest {

    @Test
    public void write() {
        TempWriteData tempWriteData = new TempWriteData();
        tempWriteData.setName("zs\r\n \\ \r\n t4");
        EasyExcel.write(TestFileUtil.getPath() + "TempWriteTest" + System.currentTimeMillis() + ".xlsx",
                TempWriteData.class)
            .sheet()
            .registerConverter(new CustomStringStringConverter())
            .doWrite(ListUtils.newArrayList(tempWriteData));


        EasyExcel.write(TestFileUtil.getPath() + "TempWriteTest" + System.currentTimeMillis() + ".xlsx",
                TempWriteData.class)
            .sheet()
            .doWrite(ListUtils.newArrayList(tempWriteData));

    }

    @Test
    public void cglib() {
        TempWriteData tempWriteData = new TempWriteData();
        tempWriteData.setName("1");
        tempWriteData.setName1("2");
        BeanMap beanMap = BeanMapUtils.create(tempWriteData);

        log.info("d1{}", beanMap.get("name"));
        log.info("d2{}", beanMap.get("name1"));

        TempWriteData tempWriteData2 = new TempWriteData();

        Map<String, String> map = new HashMap<>();
        map.put("name", "zs");
        BeanMap beanMap2 = BeanMapUtils.create(tempWriteData2);
        beanMap2.putAll(map);
        log.info("3{}", tempWriteData2.getName());

    }
}
