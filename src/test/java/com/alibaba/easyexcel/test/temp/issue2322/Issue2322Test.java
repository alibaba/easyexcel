package com.alibaba.easyexcel.test.temp.issue2322;

import java.io.File;
import java.math.BigDecimal;

import com.alibaba.easyexcel.test.demo.read.DemoData;
import com.alibaba.easyexcel.test.demo.read.DemoDataListener;
import com.alibaba.easyexcel.test.demo.read.NoModelDataListener;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.converters.string.StringNumberConverter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.NumberDataFormatterUtils;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


@Ignore
@Slf4j
public class Issue2322Test {
    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest1() {
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "temp/issue2322" + File.separator + "test.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest2() {
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "temp/issue2322" + File.separator + "test.xlsx";
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).registerConverter(new StringNumberConverter()).sheet(0).doRead();
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest3() {
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "temp/issue2322" + File.separator + "test.xlsx";
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest4() {
//        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        String fileName = TestFileUtil.getPath() + "temp/issue2322" + File.separator + "test.xlsx";
        EasyExcel.read(fileName, new NoModelDataListener()).registerConverter(new StringNumberConverter()).sheet(0).doRead();
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest5() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-m-d h:mm", null);
        assertEquals("2022-01-01 01:02", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest6() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-m-d h:mm:ss", null);
        assertEquals("2022-01-01 01:02:48", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest7() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-m-d", null);
        assertEquals("2022-01-01", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest8() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-mm-d h:mm", null);
        assertEquals("2022-01-01 01:02", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest9() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-mm-d h:mm:ss", null);
        assertEquals("2022-01-01 01:02:48", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest10() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-mm-d", null);
        assertEquals("2022-01-01", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest11() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-m-dd h:mm", null);
        assertEquals("2022-01-01 01:02", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest12() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-m-dd h:mm:ss", null);
        assertEquals("2022-01-01 01:02:48", str);
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2322
    @Test
    public void IssueTest13() {
        BigDecimal bigDecimal =new BigDecimal(44562.0436111111);
        short shortNum = 22;
        String str = NumberDataFormatterUtils.format(bigDecimal, shortNum,"yyyy-m-dd", null);
        assertEquals("2022-01-01", str);
    }
}
