package com.alibaba.easyexcel.test.core.converter;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.util.DateUtils;

/**
 * Annotation data test
 *
 * @author zhuangjiaju
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConverterDataTest {

    private File file07 = TestFileUtil.createNewFile("converter07.xlsx");
    private File file03 = TestFileUtil.createNewFile("converter03.xls");

    @Test
    public void T01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void T02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcelFactory.write(file, ConverterData.class).sheet().doWrite(data()).finish();
        EasyExcelFactory.read(file, ConverterData.class, new ConverterDataListener()).sheet().doRead().finish();
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
        list.add(converterData);
        return list;
    }
}
