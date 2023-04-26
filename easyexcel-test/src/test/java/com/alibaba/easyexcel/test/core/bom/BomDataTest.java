package com.alibaba.easyexcel.test.core.bom;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import org.apache.commons.compress.utils.Lists;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BomDataTest {
    @Test
    public void t01ReadAndWriteCsv() {
        readCsv(TestFileUtil.readFile("bom" + File.separator + "bom_none.csv"));
        readCsv(TestFileUtil.readFile("bom" + File.separator + "bom_utf8.csv"));
        readCsv(TestFileUtil.readFile("bom" + File.separator + "bom_utf16be.csv"));
        readCsv(TestFileUtil.readFile("bom" + File.separator + "bom_utf16le.csv"));
    }

    private void readCsv(File file) {
        EasyExcel.read(file, BomData.class, new ReadListener<BomData>() {

            private final List<BomData> dataList = Lists.newArrayList();

            @Override
            public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
                String head = headMap.get(0).getStringValue();
                Assert.assertEquals("姓名", head);
            }

            @Override
            public void invoke(BomData data, AnalysisContext context) {
                dataList.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                Assert.assertEquals(dataList.size(), 10);
                BomData bomData = dataList.get(0);
                Assert.assertEquals("姓名0", bomData.getName());
                Assert.assertEquals(0, (long) bomData.getAge());
            }
        }).sheet().doRead();
    }
}
