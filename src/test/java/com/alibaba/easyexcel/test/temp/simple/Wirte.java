package com.alibaba.easyexcel.test.temp.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.core.large.LargeData;
import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.fastjson.JSON;

import net.sf.cglib.beans.BeanMap;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/
@Ignore
public class Wirte {
    private static final Logger LOGGER = LoggerFactory.getLogger(Wirte.class);

    @Test
    public void simpleWrite1() {
        LargeData ss = new LargeData();
        ss.setStr23("ttt");
        Map map = BeanMap.create(ss);
        System.out.println(map.containsKey("str23"));
        System.out.println(map.containsKey("str22"));
        System.out.println(map.get("str23"));
        System.out.println(map.get("str22"));
    }

    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "t22" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).relativeHeadRowIndex(10).sheet("模板").doWrite(data());
    }

    @Test
    public void simpleWrite2() {
        // 写法1
        String fileName = TestFileUtil.getPath() + "t22" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, WriteData.class).sheet("模板").registerWriteHandler(new WriteHandler()).doWrite(data1());
    }

    @Test
    public void json() {
        JsonData jsonData = new JsonData();
        jsonData.setSS1("11");
        jsonData.setSS2("22");
        jsonData.setSs3("33");
        System.out.println(JSON.toJSONString(jsonData));

    }

    @Test
    public void json3() {
        String json = "{\"SS1\":\"11\",\"sS2\":\"22\",\"ss3\":\"33\"}";

        JsonData jsonData = JSON.parseObject(json, JsonData.class);
        System.out.println(JSON.toJSONString(jsonData));

    }

    @Test
    public void tableWrite() {
        String fileName = TestFileUtil.getPath() + "tableWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里直接写多个table的案例了，如果只有一个 也可以直一行代码搞定，参照其他案例
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        // 把sheet设置为不需要头 不然会输出sheet的头 这样看起来第一个table 就有2个头了
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        // 这里必须指定需要头，table 会继承sheet的配置，sheet配置了不需要，table 默认也是不需要
        WriteTable writeTable0 = EasyExcel.writerTable(0).head(DemoData1.class).build();
        // 第一次写入会创建头
        excelWriter.write(data(), writeSheet, writeTable0);
        // 第二次写如也会创建头，然后在第一次的后面写入数据
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = new ArrayList<String>();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("640121807369666560" + i);
            data.setDate(new Date());
            data.setDoubleData(null);
            list.add(data);
        }
        return list;
    }

    private List<WriteData> data1() {
        List<WriteData> list = new ArrayList<WriteData>();
        for (int i = 0; i < 10; i++) {
            WriteData data = new WriteData();
            data.setF(300.35f);
            list.add(data);
        }
        return list;
    }

}
