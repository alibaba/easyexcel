package com.alibaba.easyexcel.test.temp.issue2419;

import java.io.File;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
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
}
