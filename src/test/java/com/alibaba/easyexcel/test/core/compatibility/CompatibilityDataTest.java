package com.alibaba.easyexcel.test.core.compatibility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;

/**
 *
 * @author zhuangjiaju
 */
public class CompatibilityDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("compatibility07.xlsx");
        file03 = TestFileUtil.createNewFile("compatibility03.xls");
    }

    @Test
    public void T01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void T02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) throws Exception {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = EasyExcelFactory.getWriter(out);
        // sheet1 width,string head,stirng data
        Sheet sheet1 = new Sheet(1, 3);
        sheet1.setSheetName("第一个sheet");
        Map columnWidth = new HashMap();
        columnWidth.put(0, 10000);
        columnWidth.put(1, 50000);
        sheet1.setColumnWidthMap(columnWidth);
        sheet1.setHead(head());
        writer.write1(listData(), sheet1);

        // sheet2 style,class head
        Sheet sheet2 = new Sheet(2, 3, CompatibilityData.class, "第二个sheet", null);
        sheet2.setTableStyle(style());
        writer.write(data(), sheet2);

        // sheet3 table
        Sheet sheet3 = new Sheet(3, 0);
        sheet3.setSheetName("第三个sheet");

        Table table1 = new Table(1);
        table1.setHead(head());
        writer.write1(listData(), sheet3, table1);

        Table table2 = new Table(2);
        table2.setClazz(CompatibilityData.class);
        writer.write(data(), sheet3, table2);

        writer.finish();
        out.close();

        // EasyExcelFactory.write(file, AnnotationData.class).sheet().doWrite(data()).finish();
        // EasyExcelFactory.read(file, AnnotationData.class, new AnnotationDataListener()).sheet().doRead().finish();
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串标题0");
        List<String> head1 = new ArrayList<String>();
        head1.add("字符串标题1");
        list.add(head0);
        list.add(head1);
        return list;
    }

    private List<List<Object>> listData() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        List<Object> data0 = new ArrayList<Object>();
        data0.add("字符串0");
        data0.add(1);
        list.add(data0);
        return list;
    }

    private List<CompatibilityData> data() {
        List<CompatibilityData> list = new ArrayList<CompatibilityData>();
        for (int i = 0; i < 10; i++) {
            CompatibilityData data = new CompatibilityData();
            data.setString0("字符串0" + i);
            data.setString1("字符串1" + i);
            list.add(data);
        }
        return list;
    }

    public TableStyle style() {
        TableStyle tableStyle = new TableStyle();
        Font headFont = new Font();
        headFont.setBold(true);
        headFont.setFontHeightInPoints((short)22);
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.BLUE);

        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)22);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.GREEN);
        return tableStyle;
    }
}
