package com.alibaba.easyexcel.test.demo.rare;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.alibaba.easyexcel.test.demo.write.DemoData;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.RowWriteHandlerContext;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.metadata.WriteSheet;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.ehcache.Cache;
import org.ehcache.Cache.Entry;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
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
        log.info("写入到文件：{}", file.getAbsolutePath());

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

    /**
     * 在指定单元格写入数据
     */
    @Test
    public void specifiedCellWrite() {
        File file = TestFileUtil.createNewFile("rare/specifiedCellWrite" + System.currentTimeMillis()
            + ".xlsx");
        log.info("写入到文件：{}", file.getAbsolutePath());

        // 需要区分是在 最后一行之前 还是之后
        // 区分的原因是：excel只能一直向前，而且内存里面只存储100条，而afterRowDispose是在每一行写入完成的时候调用，所以修改一行需要拦截这个事件
        // 如果是在最后一行之后，由于后面不会再有数据了，所以只要拦截afterWorkbookDispose，在整个excel快写完的时候调用，继续写入数据即可

        EasyExcel.write(file, DemoData.class)
            // 写入的值在最后一行之前
            .registerWriteHandler(new RowWriteHandler() {
                @Override
                public void afterRowDispose(RowWriteHandlerContext context) {
                    if (context.getRow().getRowNum() == 2) {
                        Cell cell = context.getRow().getCell(2);
                        if (cell == null) {
                            cell = context.getRow().createCell(2);
                        }
                        cell.setCellValue("测试的第二行数据呀");
                    }
                }
            })
            // 写入的值 在最后一一行之后
            .registerWriteHandler(new WorkbookWriteHandler() {
                @Override
                public void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
                    Workbook workbook = context.getWriteWorkbookHolder().getWorkbook();
                    Sheet sheet = workbook.getSheetAt(0);
                    Row row = sheet.getRow(99);
                    if (row == null) {
                        row = sheet.createRow(99);
                    }
                    Cell cell = row.getCell(2);
                    if (cell == null) {
                        cell = row.createCell(2);
                    }
                    cell.setCellValue("测试地99行数据呀");
                }
            })
            .sheet("模板")
            .doWrite(data());

        log.info("写入到文件完成:{}", file);
    }

    /**
     * 缓存配置
     */
    private static final CacheConfiguration CACHE_CONFIGURATION = CacheConfigurationBuilder
        // 设置产品最大占用10G
        .newCacheConfigurationBuilder(String.class, ArrayList.class,
            ResourcePoolsBuilder.newResourcePoolsBuilder().disk(10, MemoryUnit.GB))
        // 最大遍历深度 设置成一直遍历
        .withSizeOfMaxObjectGraph(1000 * 1000L)
        // 最大对象为10G数据
        .withSizeOfMaxObjectSize(10, MemoryUnit.GB).build();

    /**
     *缓存存储文件的位置
     */
    private static final String CACHE_PATH = FileUtils.getTempFilePrefix() + "myCache";
    private static final  CacheManager CACHE_MANAGER = CacheManagerBuilder.newCacheManagerBuilder()
        // 这里名字随便取 路径放到临时目录
        .with(CacheManagerBuilder.persistence(CACHE_PATH))
        .build(true);

    /**
     * 在大文件后追加写入
     * 这个是无法实现的，所以思路就是临时存储到磁盘里面，然后再读取出来一次性写入
     */
    @Test
    public void appendLagreFile() {
        log.info("临时的缓存目录是:{}", CACHE_PATH);



        // 记录缓存名字
        String cacheName = UUID.randomUUID().toString();
        // 整个文件的要一起公用
        Cache<String, List<DemoData>> cache = cacheManager.createCache(cacheName, cacheConfiguration);

        for (int i = 0; i < 10000; i++) {
            // 把所有需要追加的数据放到缓存里面
            cache.put(UUID.randomUUID().toString(), data());
        }

        File file = TestFileUtil.createNewFile("rare/appendLagreFile" + System.currentTimeMillis()
            + ".xlsx");
        log.info("写入到文件：{}", file.getAbsolutePath());
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(file, DemoData.class).build()) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            Iterator<Entry<String, List<DemoData>>> iterator = cache.iterator();
            while (iterator.hasNext()) {
                excelWriter.write(iterator.next().getValue(), writeSheet);
            }
            log.info("写入数据完毕。");
        }
        cacheManager.removeCache(cacheName);
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
