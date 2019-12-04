package com.alibaba.easyexcel.test.temp.dataformat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.temp.Lock2Test;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

/**
 * 格式测试
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class DataFormatTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lock2Test.class);

    @Test
    public void test() throws Exception {
        File file = new File("D:\\test\\dataformat.xlsx");

        List<DataFormatData> list =
            EasyExcel.read(file, DataFormatData.class, null).sheet().headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (DataFormatData data : list) {
            Integer dataFormat = data.getDate().getDataFormat();

            String dataFormatString = data.getDate().getDataFormatString();

            if (dataFormat == null || dataFormatString == null) {

            } else {
                LOGGER.info("格式化：{};{}：{}", dataFormat, dataFormatString,
                    DateUtil.isADateFormat(dataFormat, dataFormatString));
            }

            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void testxls() throws Exception {
        File file = new File("D:\\test\\dataformat.xls");

        List<DataFormatData> list =
            EasyExcel.read(file, DataFormatData.class, null).sheet().headRowNumber(0).doReadSync();
        LOGGER.info("数据：{}", list.size());
        for (DataFormatData data : list) {
            Integer dataFormat = data.getDate().getDataFormat();

            String dataFormatString = data.getDate().getDataFormatString();

            if (dataFormat == null || dataFormatString == null) {

            } else {
                LOGGER.info("格式化：{};{}：{}", dataFormat, dataFormatString,
                    DateUtil.isADateFormat(dataFormat, dataFormatString));
            }

            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test3() throws IOException {
        String file = "D:\\test\\dataformat1.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
        Cell cell = xssfSheet.getRow(0).getCell(0);
        DataFormatter d = new DataFormatter();
        System.out.println(d.formatCellValue(cell));
    }

    @Test
    public void test31() throws IOException {
        System.out.println(DateUtil.isADateFormat(181, "[DBNum1][$-404]m\"\u6708\"d\"\u65e5\";@"));
    }

    @Test
    public void test43() throws IOException {
        SimpleDateFormat s = new SimpleDateFormat("yyyy'年'm'月'd'日' h'点'mm'哈哈哈m'");
        System.out.println(s.format(new Date()));
    }

    @Test
    public void test463() throws IOException {
        SimpleDateFormat s = new SimpleDateFormat("[$-804]yyyy年m月");
        System.out.println(s.format(new Date()));
    }

    @Test
    public void test1() throws Exception {
        System.out.println(DateUtil.isADateFormat(181, "yyyy\"年啊\"m\"月\"d\"日\"\\ h"));
        System.out.println(DateUtil.isADateFormat(180, "yyyy\"年\"m\"月\"d\"日\"\\ h\"点\""));
    }

    @Test
    public void test2() throws Exception {
        List<String> list1 = new ArrayList<String>(3000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list1.clear();
        }
        System.out.println("end:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list1 = new ArrayList<String>(3000);
        }
        System.out.println("end:" + (System.currentTimeMillis() - start));
    }

}
