package com.alibaba.easyexcel.test.temp.issue2419;

import java.io.File;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.excel.util.DateUtils;

import java.text.ParseException;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
@Ignore
@Slf4j
public class Issue2419Test {
    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2419
    @Test
    public void IssueTest1() {
        String fileName = TestFileUtil.getPath() + "temp/issue2419" + File.separator + "date1.xlsx";
        EasyExcel.read(fileName, Issue2419.class, new PageReadListener<Issue2419>(dataList -> {
            for (Issue2419 issueData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(issueData));
            }
        })).sheet().doRead();
    }
    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2419
    @Test
    public void IssueTest2() {
        String fileName = TestFileUtil.getPath() + "temp/issue2419" + File.separator + "date2.xlsx";
        EasyExcel.read(fileName, Issue2419.class, new PageReadListener<Issue2419>(dataList -> {
            for (Issue2419 issueData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(issueData));
            }
        })).sheet().doRead();
    }

    @Test
    public void parseDateTest1() throws ParseException {
        String dateString1 = "20220101121212";
        String dateString2 = "2022/01/01 12:12:12";
        Date date1 = DateUtils.parseDate(dateString1,"yyyyMMddHHmmss");
        Date date2 = DateUtils.parseDate(dateString2,"yyyy/MM/dd HH:mm:ss");
        assertEquals(date1, date2);
    }

    @Test
    public void parseDateTest2() throws ParseException {
        String dateString1 = "2022/01/01";
        String dateString2 = "2022-01-01";
        Date date1 = DateUtils.parseDate(dateString1,"yyyy/mm/dd");
        Date date2 = DateUtils.parseDate(dateString2,"yyyy-mm-dd");
        assertEquals(date1, date2);
    }

    @Test
    public void parseDateTest3() throws ParseException {
        String dateString1 = "2022/01/01";
        String dateString2 = "22/1/1";
        Date date1 = DateUtils.parseDate(dateString1);
        Date date2 = DateUtils.parseDate(dateString2);
        assertEquals(date1, date2);
    }

    @Test
    public void parseDateTest4() throws ParseException {
        String dateString1 = "2022-1-1";
        String dateString2 = "2022/1/1";
        Date date1 = DateUtils.parseDate(dateString1);
        Date date2 = DateUtils.parseDate(dateString2);
        assertEquals(date1, date2);
    }

    @Test
    public void switchDateFormatTest1() throws ParseException {
        String dateString = "2022-1-1";
        String dateFormat = DateUtils.switchDateFormat(dateString);
        assertEquals("yyyy-MM-dd", dateFormat);
    }

    @Test
    public void switchDateFormatTest2() throws ParseException {
        String dateString = "22/01/01";
        String dateFormat = DateUtils.switchDateFormat(dateString);
        assertEquals("yyyy/MM/dd", dateFormat);
    }


}
