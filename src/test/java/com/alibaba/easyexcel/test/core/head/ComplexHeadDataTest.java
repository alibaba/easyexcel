package com.alibaba.easyexcel.test.core.head;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;

/**
 *
 * @author Jiaju Zhuang
 */
public class ComplexHeadDataTest {

    private static File file07;
    private static File file03;
    private static File file07AutomaticMergeHead;
    private static File file03AutomaticMergeHead;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("complexHead07.xlsx");
        file03 = TestFileUtil.createNewFile("complexHead03.xls");
        file07AutomaticMergeHead = TestFileUtil.createNewFile("complexHeadAutomaticMergeHead07.xlsx");
        file03AutomaticMergeHead = TestFileUtil.createNewFile("complexHeadAutomaticMergeHead03.xls");
    }

    @Test
    public void t01ReadAndWrite07() {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) {
        EasyExcel.write(file, ComplexHeadData.class).sheet().doWrite(data());
        EasyExcel.read(file, ComplexHeadData.class, new ComplexDataListener())
            .xlsxSAXParserFactoryName("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl").sheet().doRead();
    }

    @Test
    public void t03ReadAndWriteAutomaticMergeHead07() {
        readAndWriteAutomaticMergeHead07(file07AutomaticMergeHead);
    }

    @Test
    public void t04ReadAndWriteAutomaticMergeHead0703() {
        readAndWriteAutomaticMergeHead07(file03AutomaticMergeHead);
    }

    private void readAndWriteAutomaticMergeHead07(File file) {
        EasyExcel.write(file, ComplexHeadData.class).automaticMergeHead(Boolean.FALSE).sheet().doWrite(data());
        EasyExcel.read(file, ComplexHeadData.class, new ComplexDataListener()).sheet().doRead();
    }

    private List<ComplexHeadData> data() {
        List<ComplexHeadData> list = new ArrayList<ComplexHeadData>();
        ComplexHeadData data = new ComplexHeadData();
        data.setString0("字符串0");
        data.setString1("字符串1");
        data.setString2("字符串2");
        data.setString3("字符串3");
        data.setString4("字符串4");
        list.add(data);
        return list;
    }
}
