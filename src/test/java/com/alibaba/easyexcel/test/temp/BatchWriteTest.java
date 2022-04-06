package com.alibaba.easyexcel.test.temp;

import com.alibaba.easyexcel.test.demo.read.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lwen
 * @version 1.0
 * @date 2021/11/18
 * @desc
 */
public class BatchWriteTest {


    @Test
    public static void batchWrite() {
        // 方法1 如果写到同一个sheet
        String fileName = TestFileUtil.getPath() + "batchWrite.xlsx";
        ExcelWriter excelWriter = null;
        try {
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(fileName, Map.class).head(Arrays.asList(Arrays.asList("string"),Arrays.asList("date"),Arrays.asList("doubleData"))).build();
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 1000; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    private static List<DemoData> data() {
        DemoData d = new DemoData();
        d.setDate(new Date());
        d.setDoubleData(1d);
        d.setString("adfad");
        ArrayList<DemoData> l = new ArrayList<>();
        l.add(d);
        return l;
    }


}
