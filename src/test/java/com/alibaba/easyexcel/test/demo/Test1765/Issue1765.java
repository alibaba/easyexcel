package com.alibaba.easyexcel.test.demo.Test1765;

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

public class Issue1765 {
    @Test
    public void test1765() throws FileNotFoundException {
        String fileName = "src/test/java/com/alibaba/easyexcel/test/demo/Test1765/Data1765.xlsx";
        EasyExcel.write(fileName).head(head()).sheet("模板").doWrite(dataList());
    }


    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        List<String> head1 = new ArrayList<String>();
        List<String> head2 = new ArrayList<String>();
        List<String> head3 = new ArrayList<String>();
        head0.add("表头");
        head1.add("日期");
        head2.add(null);
        head3.add("数字");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);

        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 5; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(i * 2);
            data.add(i * 3);
            list.add(data);
        }
        return list;
    }
}
