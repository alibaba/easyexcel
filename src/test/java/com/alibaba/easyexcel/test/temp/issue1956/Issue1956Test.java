package com.alibaba.easyexcel.test.temp.issue1956;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.NumberUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.text.ParseException;

@Ignore
@Slf4j
public class Issue1956Test {
    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/1956
    @Test
    public void IssueTest1() {
        String fileName = TestFileUtil.getPath() + "temp/issue1956" + File.separator + "date1.xlsx";
        EasyExcel.read(fileName, Issue1956.class, new PageReadListener<Issue1956>(dataList -> {
            for (Issue1956 issueData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(issueData));
            }
        })).sheet().doRead();
    }

    //CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/1956
    @Test
    public void IssueTest2() {
        String fileName = TestFileUtil.getPath() + "temp/issue1956" + File.separator + "date2.xlsx";
        EasyExcel.read(fileName, Issue1956.class, new PageReadListener<Issue1956>(dataList -> {
            for (Issue1956 issueData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(issueData));
            }
        })).sheet().doRead();
    }

}
