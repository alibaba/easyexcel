package com.alibaba.easyexcel.test.temp.bug;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.excel.EasyExcel;

public class RecordHandlerTest {
    @Test
    public void xls03CurrentSheetMissTest() throws IOException {
        ClassPathResource resource = new ClassPathResource("bug/currentSheetMiss.xls");
        InputStream stream = resource.getInputStream();
        List<Object> objects = EasyExcel.read(stream).sheet().sheetName("检测记录表3月").doReadSync();
        Assert.assertEquals(1771, objects.size());

    }
}
