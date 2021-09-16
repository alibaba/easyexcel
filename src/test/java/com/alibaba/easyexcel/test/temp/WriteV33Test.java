package com.alibaba.easyexcel.test.temp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.demo.write.IndexData;
import com.alibaba.easyexcel.test.temp.data.DataType;
import com.alibaba.easyexcel.test.temp.data.HeadType;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 临时测试
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class WriteV33Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteV33Test.class);

    @Test
    public void test() throws Exception {
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(2, 2, 0, 1);

        // 这里 指定文件
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).registerWriteHandler(
            onceAbsoluteMergeStrategy).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "模板1").build();
        WriteSheet writeSheet2 = EasyExcel.writerSheet(2, "模板2").build();
        WriteSheet writeSheet3 = EasyExcel.writerSheet(3, "模板3").build();
        excelWriter.write(data(2), writeSheet2);
        excelWriter.write(data(3), writeSheet3);
        excelWriter.write(data(1), writeSheet1);
        excelWriter.write(data(3), writeSheet3);
        excelWriter.write(data(1), writeSheet1);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private List<DemoData> data(int no) {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + no + "---" + i);
            list.add(data);
        }
        return list;
    }

    @Test
    public void test33() throws Exception {
        List<DataType> data = getData();
        String fileName = TestFileUtil.getPath() + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";

        ExcelWriter excelWriter = null;
        excelWriter = EasyExcel.write(fileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(1, "test")
            .head(HeadType.class)
            .build();
        excelWriter.write(data, writeSheet);
        excelWriter.finish();
    }

    private List<DataType> getData() {
        DataType vo = new DataType();
        vo.setId(738);
        vo.setFirstRemark("1222");
        vo.setSecRemark("22222");
        return Collections.singletonList(vo);
    }

    @Test
    public void indexWrite() {
        String fileName = TestFileUtil.getPath() + "indexWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, IndexData.class)
            .excludeColumnIndexes(Collections.singleton(0))
            .sheet("模板")
            .excludeColumnIndexes(Collections.singleton(1))
            .doWrite(indexData());
    }

    private List<IndexData> indexData() {
        List<IndexData> list = new ArrayList<IndexData>();
        for (int i = 0; i < 10; i++) {
            IndexData data = new IndexData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
