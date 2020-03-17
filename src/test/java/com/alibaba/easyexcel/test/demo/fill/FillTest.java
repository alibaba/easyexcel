package com.alibaba.easyexcel.test.demo.fill;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

/**
 * 写的填充写法
 *
 * @since 2.1.1
 * @author Jiaju Zhuang
 */
@Ignore
public class FillTest {
    /**
     * 最简单的填充
     *
     * @since 2.1.1
     */
    @Test
    public void simpleFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "simple.xlsx";

        // 方案1 根据对象填充
        String fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);

        // 方案2 根据Map填充
        fileName = TestFileUtil.getPath() + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }

    /**
     * 填充列表
     *
     * @since 2.1.1
     */
    @Test
    public void listFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // 填充list 的时候还要注意 模板中{.} 多了个点 表示list
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "list.xlsx";

        // 方案1 一下子全部放到内存里面 并填充
        String fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(data());

        // 方案2 分多次 填充 会使用文件缓存（省内存）
        fileName = TestFileUtil.getPath() + "listFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(data(), writeSheet);
        excelWriter.fill(data(), writeSheet);
        // 千万别忘记关闭流
        excelWriter.finish();
    }

    /**
     * 复杂的填充
     *
     * @since 2.1.1
     */
    @Test
    public void complexFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "complex.xlsx";

        String fileName = TestFileUtil.getPath() + "complexFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
        // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
        // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
        // 如果数据量大 list不是最后一行 参照下一个
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(data(), fillConfig, writeSheet);
        excelWriter.fill(data(), fillConfig, writeSheet);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        map.put("total", 1000);
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
    }

    /**
     * 数据量大的复杂填充
     * <p>
     * 这里的解决方案是 确保模板list为最后一行，然后再拼接table.还有03版没救，只能刚正面加内存。
     *
     * @since 2.1.1
     */
    @Test
    public void complexFillWithTable() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        // 这里模板 删除了list以后的数据，也就是统计的这一行
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "complexFillWithTable.xlsx";

        String fileName = TestFileUtil.getPath() + "complexFillWithTable" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 直接写入数据
        excelWriter.fill(data(), writeSheet);
        excelWriter.fill(data(), writeSheet);

        // 写入list之前的数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        excelWriter.fill(map, writeSheet);

        // list 后面还有个统计 想办法手动写入
        // 这里偷懒直接用list 也可以用对象
        List<List<String>> totalListList = new ArrayList<List<String>>();
        List<String> totalList = new ArrayList<String>();
        totalListList.add(totalList);
        totalList.add(null);
        totalList.add(null);
        totalList.add(null);
        // 第四列
        totalList.add("统计:1000");
        // 这里是write 别和fill 搞错了
        excelWriter.write(totalListList, writeSheet);
        excelWriter.finish();
        // 总体上写法比较复杂 但是也没有想到好的版本 异步的去写入excel 不支持行的删除和移动，也不支持备注这种的写入，所以也排除了可以
        // 新建一个 然后一点点复制过来的方案，最后导致list需要新增行的时候，后面的列的数据没法后移，后续会继续想想解决方案
    }

    /**
     * 横向的填充
     *
     * @since 2.1.1
     */
    @Test
    public void horizontalFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "horizontal.xlsx";

        String fileName = TestFileUtil.getPath() + "horizontalFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        excelWriter.fill(data(), fillConfig, writeSheet);
        excelWriter.fill(data(), fillConfig, writeSheet);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        excelWriter.fill(map, writeSheet);

        // 别忘记关闭流
        excelWriter.finish();
    }

    /**
     * 多列表组合填充填充
     *
     * @since 2.2.0-beta1
     */
    @Test
    public void compositeFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量 {前缀.} 前缀可以区分不同的list
        String templateFileName =
            TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "composite.xlsx";

        String fileName = TestFileUtil.getPath() + "compositeFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
        excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data1", data()), fillConfig, writeSheet);
        excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
        excelWriter.fill(new FillWrapper("data2", data()), writeSheet);
        excelWriter.fill(new FillWrapper("data3", data()), writeSheet);
        excelWriter.fill(new FillWrapper("data3", data()), writeSheet);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        excelWriter.fill(map, writeSheet);

        // 别忘记关闭流
        excelWriter.finish();
    }

    private List<FillData> data() {
        List<FillData> list = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            list.add(fillData);
            fillData.setName("张三");
            fillData.setNumber(5.2);
        }
        return list;
    }
}
