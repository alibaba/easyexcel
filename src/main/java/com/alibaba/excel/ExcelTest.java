package com.alibaba.excel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.apache.commons.codec.DecoderException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {

    @Data
    @ExcelIgnoreUnannotated
    static class Header {
        @ExcelProperty("学生姓名")
        private String studentName;
        @ExcelProperty("所属学校")
        private String schoolName;
        @ExcelProperty("课班名称")
        private String className;
    }

    public static void main(String[] args) throws IOException, DecoderException {
        File file = new File("C:\\Users\\Tan\\easyexcel\\src\\test\\resources\\demo\\demo.xlsx");
        EasyExcel.write(file, null).sheet().doWrite(() -> {
            List<Header> result = new ArrayList<>();
            Header target = new Header();
            target.setClassName("测试课班");
            target.setSchoolName("测试学校");
            target.setStudentName("小张");
            result.add(target);
            return result;
        }, 3);
    }
}
