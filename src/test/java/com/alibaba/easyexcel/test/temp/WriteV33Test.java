package com.alibaba.easyexcel.test.temp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;

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
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).registerWriteHandler(onceAbsoluteMergeStrategy).build();
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

}
