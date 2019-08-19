package com.alibaba.easyexcel.test.core.converter;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.DateUtils;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConverterDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("converter07.xlsx");
        file03 = TestFileUtil.createNewFile("converter03.xls");
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
        EasyExcel.write(file, ConverterData.class).sheet().doWrite(data());
        EasyExcel.read(file, ConverterData.class, new ConverterDataListener()).sheet().doRead();
    }

    @Test
    public void T03ReadAllConverter07() {
        readAllConverter("converter" + File.separator + "converter07.xlsx");
    }

    @Test
    public void T04ReadAllConverter03() {
        readAllConverter("converter" + File.separator + "converter03.xls");
    }

    private void readAllConverter(String fileName) {
        EasyExcel
            .read(TestFileUtil.readFile(fileName), ReadAllConverterData.class, new ReadAllConverterDataListener())
            .sheet().doRead();
    }

    private List<ConverterData> data() throws Exception {
        List<ConverterData> list = new ArrayList<ConverterData>();
        ConverterData converterData = new ConverterData();
        converterData.setDate(DateUtils.parseDate("2020-01-01 01:01:01"));
        converterData.setBooleanData(Boolean.TRUE);
        converterData.setBigDecimal(BigDecimal.ONE);
        converterData.setLongData(1L);
        converterData.setIntegerData(1);
        converterData.setShortData((short)1);
        converterData.setByteData((byte)1);
        converterData.setDoubleData(1.0);
        converterData.setFloatData((float)1.0);
        converterData.setString("测试");
        converterData.setCellData(new CellData("自定义"));
        list.add(converterData);
        return list;
    }
}
