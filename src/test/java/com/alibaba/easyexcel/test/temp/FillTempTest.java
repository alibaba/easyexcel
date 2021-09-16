package com.alibaba.easyexcel.test.temp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.easyexcel.test.demo.fill.FillData;
import com.alibaba.easyexcel.test.temp.fill.FillData2;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;

/**
 * 写的填充写法
 *
 * @since 2.1.1
 * @author Jiaju Zhuang
 */
@Ignore
public class FillTempTest {

    /**
     * 复杂的填充
     *
     * @since 2.1.1
     */
    @Test
    public void complexFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(2, 2, 0, 1);

        String fileName = TestFileUtil.getPath() + "complexFill" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).registerWriteHandler(onceAbsoluteMergeStrategy).withTemplate(TestFileUtil.readUserHomeFile("test/simple.xlsx")).build();
        WriteSheet writeSheet0 = EasyExcel.writerSheet(0).build();
        WriteSheet writeSheet1 = EasyExcel.writerSheet(1).build();

        excelWriter.fill(teamp(), writeSheet0);
        excelWriter.fill(teamp(), writeSheet1);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        map.put("total", 1000);
        excelWriter.fill(map, writeSheet0);

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
        String templateFileName = "D:\\test\\complex.xlsx";

        String fileName = TestFileUtil.getPath() + "complexFillWithTable" + System.currentTimeMillis() + ".xlsx";
        ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 直接写入数据
        excelWriter.fill(data(), writeSheet);
        excelWriter.fill(data2(), writeSheet);

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

    private List<FillData2> data2() {
        List<FillData2> list = new ArrayList<FillData2>();
        for (int i = 0; i < 10; i++) {
            FillData2 fillData = new FillData2();
            list.add(fillData);
            fillData.setTest("ttttttt" + i);
        }
        return list;
    }

    private List<TempFillData> teamp() {
        List<TempFillData> list = new ArrayList<TempFillData>();
        for (int i = 0; i < 10; i++) {
            TempFillData fillData = new TempFillData();
            list.add(fillData);
            fillData.setName("张三");
            fillData.setNumber(5.2);
        }
        return list;
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
