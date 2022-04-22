package com.alibaba.easyexcel.test.temp.issue2322;

import java.io.File;

import com.alibaba.easyexcel.test.demo.read.DemoData;
import com.alibaba.easyexcel.test.demo.read.DemoDataListener;
import com.alibaba.easyexcel.test.demo.read.NoModelDataListener;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.converters.string.StringNumberConverter;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;


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
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            // 读取一个sheet
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

}
