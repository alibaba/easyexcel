package com.alibaba.easyexcel.test.demo.rare;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteSheet;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 记录一些不太常见的案例
 *
 * @author Jiaju Zhuang
 */
@Ignore
@Slf4j
public class WriteTest {

    /**
     * 压缩临时文件
     * 在导出Excel且格式为xlsx的时候会生成一个临时的xml文件，会比较大，再磁盘不太够的情况下，可以压缩。
     * 当然压缩式耗费性能的
     */
    @Test
    public void compressedTemporaryFile() {
        log.info("临时的xml存储在:{}", FileUtils.getPoiFilesPath());
        File file = TestFileUtil.createNewFile("rare/compressedTemporaryFile" + System.currentTimeMillis()
            + ".xlsx");

        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(file, DemoData.class).registerWriteHandler(
            new WorkbookWriteHandler() {

                /**
                 * 拦截Workbook创建完成事件
                 * @param context
                 */
                @Override
                public void afterWorkbookCreate(WorkbookWriteHandlerContext context) {
                    // 获取到Workbook对象
                    Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
                    // 只有SXSSFWorkbook模式才会生成临时文件
                    if (workbook instanceof SXSSFWorkbook) {
                        SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook)workbook;
                        // 设置临时文件压缩，当然这个会浪费cpu性能 但是临时文件会变小
                        sxssfWorkbook.setCompressTempFiles(true);
                    }
                }
            }).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            // 10万数据 确保有足够的空间
            for (int i = 0; i < 10000; i++) {
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                List<DemoData> data = data();
                excelWriter.write(data, writeSheet);
            }
            log.info("写入完毕，开始准备迁移压缩文件。");
        }
    }

    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

}
