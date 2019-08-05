package com.alibaba.easyexcel.test.demo.read;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

/**
 * 读的常见写法
 *
 * @author zhuangjiaju
 */
@Ignore
public class ReadTest {
    /**
     * 最简单的读
     * <li>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <li>2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <li>3. 直接读即可
     */
    @Test
    public void simpleRead() {
        // 写法1：
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 然后千万别忘记 finish
        EasyExcelFactory.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead().finish();

        // 写法2：
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = EasyExcelFactory.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcelFactory.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
    }

    /**
     * 指定列的下标或者列名
     * <li>使用{@link com.alibaba.excel.annotation.ExcelProperty}注解即可
     */
    @Test
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里默认读取第一个sheet 然后千万别忘记 finish
        EasyExcelFactory.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).sheet().doRead().finish();
    }
}
