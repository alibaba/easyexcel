package com.alibaba.easyexcel.test.demo.Test1662;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Issue1662 {
    @Test
    public void test1662() {
        String fileName = TestFileUtil.getPath() + "Test1662" + ".xlsx";
        System.out.println(fileName);
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }


    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        List<String> head1 = new ArrayList<String>();
        List<String> head2 = new ArrayList<String>();
        head0.add("xx");
        head0.add("日期");
        list.add(head0);
//        head1.add("日期");
        head1.add("日期");
        list.add(head1);
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