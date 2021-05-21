package com.alibaba.easyexcel.test.temp.issue1579;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.demo.write.DemoDataListener;
import com.alibaba.easyexcel.test.demo.write.ReadDemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*针对issue1579，提出了一种解决方案
* 增加一个方法使用户可以通过传参的方式更改输出的表格的行数和列数标签
* 原有的方法会导致不管最终表格行列数多大
* 读取到的行数始终为1
* 测试需要按照代码书写的顺序进行
* 即先跑write的测试再跑相应的read测试
* 一共3组测试，共计12个测试
* */

public class WriteAndReadTest {
    /*测试数据1
    * */
    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /*测试数据2
     * */
    private List<DemoData> data2() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 19; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.1928);
            list.add(data);
        }
        return list;
    }
    /*测试数据3
     * */
    private List<DemoData> data3() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 29; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.2021);
            list.add(data);
        }
        return list;
    }
    /* 原方法输出测试1
     * 本测试用于测试调用原工程方法输出一个excel表格
     * */
    @Test
    public void OriginalWrite() {
        String fileName = TestFileUtil.getPath() + "OriginalWrite" + "1" + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            excelWriter.write(data(), writeSheet);

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }

        }
    }

    /* 原方法读取测试1
     * 本测试用于测试读取原有方法输出的表格
     * 通过info可以查看到统计到读入excel表的总行数
     * 不管输出的excel表格大小如何
     * 最终显示的读取总行数总是1
     * */
    @Test
    public void OriginalRead() {
        String fileName = TestFileUtil.getPath() + "OriginalWrite" + "1" + ".xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, ReadDemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();

            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    /* 原方法输出测试2
     * 本测试用于测试调用原工程方法输出一个excel表格
     * */
    @Test
    public void OriginalWrite2() {
        String fileName = TestFileUtil.getPath() + "OriginalWrite" + "2" + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            excelWriter.write(data2(), writeSheet);

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }

        }
    }

    /* 原方法读取测试2
     * 本测试用于测试读取原有方法输出的表格
     * 通过info可以查看到统计到读入excel表的总行数
     * 不管输出的excel表格大小如何
     * 最终显示的读取总行数总是1
     * */
    @Test
    public void OriginalRead2() {
        String fileName = TestFileUtil.getPath() + "OriginalWrite" + "2" + ".xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, ReadDemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();

            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    /* 原方法输出测试3
     * 本测试用于测试调用原工程方法输出一个excel表格
     * */
    @Test
    public void OriginalWrite3() {
        String fileName = TestFileUtil.getPath() + "OriginalWrite" + "3" + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("sheet1").build();
            excelWriter.write(data3(), writeSheet);

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }

        }
    }

    /* 原方法读取测试3
     * 本测试用于测试读取原有方法输出的表格
     * 通过info可以查看到统计到读入excel表的总行数
     * 不管输出的excel表格大小如何
     * 最终显示的读取总行数总是1
     * */
    @Test
    public void OriginalRead3() {
        String fileName = TestFileUtil.getPath() + "OriginalWrite" + "3" + ".xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, ReadDemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();

            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }

    /*新方法输出测试1
    * 本测试用于测试调用新方法输出一个excel表格
    * 用户可以传入参数规定列数行数从而修改excel中的相应标签
    * 使得最终读取到的总行数和用户规定的一样
    * */
    @Test
    public void WriteWithColAndRow() {
        String fileName = TestFileUtil.getPath() + "WriteWithColAndRow" + "1" + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheetWithColAndRow("sheet1",3,11).build();
            excelWriter.write(data(), writeSheet);

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }

        }
    }
    /* 新方法读取测试1
     * 本测试用于测试读取原有方法输出的表格
     * 通过info可以查看到统计到读入excel表的总行数
     * */
    @Test
    public void NewRead() {
        String fileName = TestFileUtil.getPath() + "WriteWithColAndRow" + "1" + ".xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, ReadDemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();

            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }
    /*新方法输出测试2
     * 本测试用于测试调用新方法输出一个excel表格
     * 用户可以传入参数规定列数行数从而修改excel中的相应标签
     * 使得最终读取到的总行数和用户规定的一样
     * */
    @Test
    public void WriteWithColAndRow2() {
        String fileName = TestFileUtil.getPath() + "WriteWithColAndRow" + "2" + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheetWithColAndRow("sheet1",3,20).build();
            excelWriter.write(data2(), writeSheet);

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }

        }
    }
    /* 新方法读取测试2
     * 本测试用于测试读取原有方法输出的表格
     * 通过info可以查看到统计到读入excel表的总行数
     * */
    @Test
    public void NewRead2() {
        String fileName = TestFileUtil.getPath() + "WriteWithColAndRow" + "2" + ".xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, ReadDemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();

            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }
    /*新方法输出测试3
     * 本测试用于测试调用新方法输出一个excel表格
     * 用户可以传入参数规定列数行数从而修改excel中的相应标签
     * 使得最终读取到的总行数和用户规定的一样
     * */
    @Test
    public void WriteWithColAndRow3() {
        String fileName = TestFileUtil.getPath() + "WriteWithColAndRow" + "3" + ".xlsx";
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(fileName, DemoData.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheetWithColAndRow("sheet1",3,30).build();
            excelWriter.write(data3(), writeSheet);

        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }

        }
    }
    /* 新方法读取测试3
     * 本测试用于测试读取原有方法输出的表格
     * 通过info可以查看到统计到读入excel表的总行数
     * */
    @Test
    public void NewRead3() {
        String fileName = TestFileUtil.getPath() + "WriteWithColAndRow" + "3" + ".xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, ReadDemoData.class, new DemoDataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();

            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }
    }
}
