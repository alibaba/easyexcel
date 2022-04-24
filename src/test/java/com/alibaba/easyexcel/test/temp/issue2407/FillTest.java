package com.alibaba.easyexcel.test.temp.issue2407;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Map;

//CS304 (manually written) Issue link: https://github.com/alibaba/easyexcel/issues/2407
@Ignore
public class FillTest {

    @Test
    public void simpleFillTest() {
        // this test case shows the correct answer
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName =
            TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleIssue.xlsx";

        String fileName = TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleFillIssue" + System.currentTimeMillis() + ".xlsx";
        //测试代码
        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName,FillData.class).withTemplate(templateFileName).sheet().doFill(fillData);

        fileName = TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleFillMapIssue" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }

    @Test
    public void simpleFillTest1() {
        String templateFileName =
            TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleIssue1.xlsx";

        String fileName = TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleFillIssue1" + System.currentTimeMillis() + ".xlsx";

        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName,FillData.class).withTemplate(templateFileName).sheet().doFill(fillData);

        fileName = TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleFillMapIssue1" + System.currentTimeMillis() + ".xlsx";

        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }

    @Test
    public void simpleFillTest2() {
        String templateFileName =
            TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleIssue2.xlsx";

        String fileName = TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleFillIssue2" + System.currentTimeMillis() + ".xlsx";

        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName,FillData.class).withTemplate(templateFileName).sheet().doFill(fillData);

        fileName = TestFileUtil.getPath() + "temp/issue2407" + File.separator + "simpleFillMapIssue2" + System.currentTimeMillis() + ".xlsx";

        Map<String, Object> map = MapUtils.newHashMap();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }
}
