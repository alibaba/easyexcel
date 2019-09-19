package com.alibaba.easyexcel.test.core.celldata;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.core.simple.SimpleData;
import com.alibaba.easyexcel.test.core.simple.SimpleDataListener;
import com.alibaba.easyexcel.test.core.simple.SimpleDataSheetNameListener;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.DateUtils;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CellDataDataTest {

    private static File file07;
    private static File file03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("cellData07.xlsx");
        file03 = TestFileUtil.createNewFile("cellData03.xls");
    }

    @Test
    public void t01ReadAndWrite07() throws Exception {
        readAndWrite(file07);
    }

    @Test
    public void t02ReadAndWrite03() throws Exception {
        readAndWrite(file03);
    }

    private void readAndWrite(File file) throws Exception {
        EasyExcel.write(file, CellDataData.class).sheet().doWrite(data());
        EasyExcel.read(file, CellDataData.class, new CellDataDataListener()).sheet().doRead();
    }

    private List<CellDataData> data() throws Exception {
        List<CellDataData> list = new ArrayList<CellDataData>();
        CellDataData cellDataData = new CellDataData();
        cellDataData.setDate(new CellData<Date>(DateUtils.parseDate("2020-01-01 01:01:01")));
        CellData<Integer> integer1 = new CellData<Integer>();
        integer1.setType(CellDataTypeEnum.NUMBER);
        integer1.setNumberValue(BigDecimal.valueOf(2L));
        cellDataData.setInteger1(integer1);
        cellDataData.setInteger2(2);
        CellData formulaValue = new CellData();
        formulaValue.setFormula(Boolean.TRUE);
        formulaValue.setFormulaValue("B2+C2");
        cellDataData.setFormulaValue(formulaValue);
        list.add(cellDataData);
        return list;
    }
}
