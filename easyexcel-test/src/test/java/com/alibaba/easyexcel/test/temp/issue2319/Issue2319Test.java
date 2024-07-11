package com.alibaba.easyexcel.test.temp.issue2319;

import java.io.File;
import java.math.BigDecimal;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.format.DataFormatter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.NumberDataFormatterUtils;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
@Slf4j
public class Issue2319Test {
    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2319
    @Test
    public void IssueTest1() {
        String fileName = TestFileUtil.getPath() + "temp/issue2319" + File.separator + "test1.xlsx";
        EasyExcel.read(fileName, Issue2319.class, new PageReadListener<Issue2319>(dataList -> {
            for (Issue2319 issueData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(issueData));
            }
        })).sheet().doRead();
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2319
    @Test
    public void IssueTest2() {
        String fileName = TestFileUtil.getPath() + "temp/issue2319" + File.separator + "test2.xlsx";
        EasyExcel.read(fileName, Issue2319.class, new PageReadListener<Issue2319>(dataList -> {
            for (Issue2319 issueData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(issueData));
            }
        })).sheet().doRead();
    }

}
