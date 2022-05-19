package com.alibaba.easyexcel.test.demo.write;
import com.alibaba.excel.annotation.ExcelListProperty;
import com.alibaba.excel.annotation.ExcelProperty;

import java.util.List;

public class issue1575TestClass {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private int age;
    @ExcelProperty("课程")
    @ExcelListProperty()
    private List<String> lessonList;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public List<String> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<String> lessonList) {
        this.lessonList = lessonList;
    }


}
