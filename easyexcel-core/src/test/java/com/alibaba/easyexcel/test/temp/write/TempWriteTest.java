package com.alibaba.easyexcel.test.temp.write;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TempWriteTest {

    @Test
    public void write() {
        TempWriteData tempWriteData = new TempWriteData();
        tempWriteData.setName("zs\r\n \\ \r\n t4");
        EasyExcel.write(TestFileUtil.getPath() + "TempWriteTest" + System.currentTimeMillis() + ".xlsx",
                TempWriteData.class)
            .sheet()
            .doWrite(ListUtils.newArrayList(tempWriteData));
    }
}
