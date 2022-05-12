package com.alibaba.easyexcel.test.temp;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.core.large.LargeData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
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
public class WriteLargeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteLargeTest.class);

    @Test
    public void test() throws Exception {
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = TestFileUtil.getPath() + "large" + System.currentTimeMillis() + ".xlsx";
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)20);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

        ExcelWriter excelWriter = EasyExcel.write(fileName, LargeData.class).registerWriteHandler(
            horizontalCellStyleStrategy).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} fill success.", j);
        }
        excelWriter.finish();

    }

    @Test
    public void test2() throws Exception {
        // 方法2 如果写到不同的sheet 同一个对象
        String fileName = TestFileUtil.getPath() + "large" + System.currentTimeMillis() + ".xlsx";

        ExcelWriter excelWriter = EasyExcel.write(fileName, LargeData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        for (int j = 0; j < 100; j++) {
            excelWriter.write(data(), writeSheet);
            LOGGER.info("{} fill success.", j);
        }
        excelWriter.finish();

    }

    private List<List<String>> data() {
        List<List<String>> list = new ArrayList<>();

        for (int j = 0; j < 10000; j++) {
            List<String> oneRow = new ArrayList<>();
            for (int i = 0; i < 150; i++) {
                oneRow.add("这是测试字段" + i);
            }
            list.add(oneRow);
        }

        return list;
    }
}
