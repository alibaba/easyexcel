package com.alibaba.easyexcel.test.core.fill;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.core.style.StyleData;
import com.alibaba.easyexcel.test.core.style.StyleDataListener;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FillDataTest {

    private static File file07;
    private static File file03;
    private static File simpleTemplate07;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("fill07.xlsx");
        file03 = TestFileUtil.createNewFile("fill03.xls");
        simpleTemplate07 = TestFileUtil.readFile("fill" + File.separator + "simple.xlsx");
    }

    @Test
    public void t01Fill07() {
        fill(file07);
    }

    @Test
    public void t02Fill03() {
        fill(file03);
    }

    private void fill(File file) {
        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(file).withTemplate(simpleTemplate07).sheet().doFill(fillData);
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

    private List<StyleData> data10() {
        List<StyleData> list = new ArrayList<StyleData>();
        for (int i = 0; i < 10; i++) {
            StyleData data = new StyleData();
            data.setString("字符串0");
            data.setString1("字符串01");
            list.add(data);
        }
        return list;
    }
}
