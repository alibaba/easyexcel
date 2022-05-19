package com.alibaba.easyexcel.test.demo.write;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.util.ListUtils;
import org.junit.Test;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

public class issue1575Test {
    @Test
    public void writeList(){
        String fileName = TestFileUtil.getPath() + "simpleWriteList" + System.currentTimeMillis() + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, issue1575TestClass.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(data(), writeSheet);
        }
    }

    private List<issue1575TestClass> data() {
        List<issue1575TestClass> list = ListUtils.newArrayList();
        for (int i = 0; i < 5; i++) {
            issue1575TestClass data = new issue1575TestClass();
            data.setName("张三" + i);
            data.setGender("男");
            data.setAge(40);
            List<String> lessonList = new ArrayList<>();
            lessonList.add("高数");
            lessonList.add("大物");
            data.setLessonList(lessonList);
            list.add(data);
        }
        return list;
    }
}
