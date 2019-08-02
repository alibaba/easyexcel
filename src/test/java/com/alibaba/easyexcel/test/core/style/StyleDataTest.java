package com.alibaba.easyexcel.test.core.style;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

/**
 *
 * @author zhuangjiaju
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StyleDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("style07.xlsx");
        file03 = TestFileUtil.createNewFile("style03.xls");
    }

    @Test
    public void T01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void T02ReadAndWrite03() {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) {
        SimpleColumnWidthStyleStrategy simpleColumnWidthStyleStrategy = new SimpleColumnWidthStyleStrategy(50);
        SimpleRowHeightStyleStrategy simpleRowHeightStyleStrategy =
            new SimpleRowHeightStyleStrategy((short)40, (short)50);

        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short)20);
        headWriteCellStyle.setWriteFont(contentWriteFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
            new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        EasyExcelFactory.write(file, StyleData.class).registerWriteHandler(simpleColumnWidthStyleStrategy)
            .registerWriteHandler(simpleRowHeightStyleStrategy).registerWriteHandler(horizontalCellStyleStrategy)
            .sheet().doWrite(data()).finish();
        EasyExcelFactory.read(file, StyleData.class, new StyleDataListener()).sheet().doRead().finish();
    }

    private List<StyleData> data() {
        List<StyleData> list = new ArrayList<StyleData>();
        StyleData data = new StyleData();
        data.setString("字符串0");
        data.setString1("字符串01");
        StyleData data1 = new StyleData();
        data1.setString("字符串1");
        data1.setString1("字符串11");
        list.add(data);
        list.add(data1);
        return list;
    }
}
