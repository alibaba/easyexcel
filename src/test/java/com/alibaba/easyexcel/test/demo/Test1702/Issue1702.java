package com.alibaba.easyexcel.test.demo.Test1702;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Issue1702 {
    @Test
    public void test1702() throws FileNotFoundException {

//        String fileName = TestFileUtil.getPath() + "TestIssue" + File.separator + "Date1702.xlsx";
        String fileName =  "src/test/java/com/alibaba/easyexcel/test/demo/Test1702/Date1702.xlsx";
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
        InputStream inputStream = new FileInputStream(fileName);
        List<Date1702> list = EasyExcelFactory.read(inputStream).head(Date1702.class).sheet(0).doReadSync();
        Assert.assertEquals("字符串",list.get(0).getStr());
    }


    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("表头");
        list.add(head0);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        List<Object> data = new ArrayList<Object>();
        data.add("字符串");
        data.add(new Date());
        data.add(0.56);
        list.add(data);
        return list;
    }
}
