package com.alibaba.easyexcel.test.temp.issue2212;

import java.io.File;
import java.util.*;

import com.alibaba.easyexcel.test.demo.fill.FillData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

import org.junit.Ignore;
import org.junit.Test;
public class FillTest2212 {
    @Test
    public void TestFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "list.xlsx";

        System.out.println(templateFileName);
        // 根据Map填充
        String fileName = TestFileUtil.getPath() + "simpleFill2212" + ".xlsx";
        System.out.println(fileName);

        // 这里 会填充到第一个sheet
        ArrayList<Map<String, Object>> mapList=new ArrayList<>();
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        //map.put("number", 5.2);
        map.put("date",new Date());
        mapList.add(map);
        Map<String, Object> map1 = MapUtils.newHashMap();
        map1.put("name", "李四");
        map1.put("number", 4.5);
        map1.put("date",new Date());
        mapList.add(map1);

        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(mapList, writeSheet);
        excelWriter.finish();
    }

}
