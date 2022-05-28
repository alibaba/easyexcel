package com.alibaba.easyexcel.test.core.converter;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.DateUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * 针对pr2502进行的测试,涉及到converterWriteData_2502.class和converterDataListener_2502
 *ttps://github.com/alibaba/easyexcel/pull/2502
 *      * https://github.com/alibaba/easyexcel/issues/2501
 * @author SUSTech-Tung
 * @date 2022/05/28
 */
public class pr_2502_test {
    private static File file07;
    private static File file03;
    private static File fileCsv;

    /**
     * 初始化 创建三个类型的文件作为测试
     *
     */
    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("localtimeconvert" + File.separator + "converter07_local.xlsx");
        file03 = TestFileUtil.createNewFile("localtimeconvert" + File.separator + "converter03_local.xls");
        fileCsv = TestFileUtil.createNewFile("localtimeconvert" + File.separator + "converterCsv_local.csv");
    }

    /**
     * 处理Apia时区的时间转换，于XLSX文件中
     *
     * @throws Exception 异常
     */
    @Test
    public void testforxlsxinMIT() throws Exception {
        List<converterWriteData_2502> list = new ArrayList<converterWriteData_2502>();
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setDate(DateUtils.parseDate("2021-2-01","yyyy-MM-dd"));
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2023-9-1 23:7:4", "yyyy-MM-dd HH:mm:ss","MIT"));
        list.add(converterWriteData);
        EasyExcel.write(file07, converterWriteData_2502.class).sheet().doWrite(list);
        EasyExcel.read(file07, converterWriteData_2502.class, new converterDataListener_2502()).sheet().doRead();
    }

    /**
     * 处理印第安时间时间转换，于XLS文件中
     *
     * @throws Exception 异常
     */
    @Test
    public void testforXLSinIET() throws Exception {
        List<converterWriteData_2502> list = new ArrayList<converterWriteData_2502>();
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setDate(DateUtils.parseDate("2020-01-01 1:01:01","yyyy-MM-dd HH:mm:ss"));
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2023-9-1 23:7:4", "yyyy-MM-dd HH:mm:ss","IET"));
        list.add(converterWriteData);
        EasyExcel.write(file03, converterWriteData_2502.class).sheet().doWrite(list);
        EasyExcel.read(file03, converterWriteData_2502.class, new converterDataListener_2502()).sheet().doRead();
    }

    /**
     * 处理非洲时间，于CSV中
     *
     * @throws Exception 异常
     */
    @Test
    public void testforCSVinEAT() throws Exception {
        List<converterWriteData_2502> list = new ArrayList<converterWriteData_2502>();
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setDate(DateUtils.parseDate("2020-01-01 1:01:01","yyyy-MM-dd HH:mm:ss"));
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2021-12-1 13:5:2", "yyyy-MM-dd HH:mm:ss","EAT"));
        list.add(converterWriteData);
        EasyExcel.write(fileCsv, converterWriteData_2502.class).sheet().doWrite(list);
        EasyExcel.read(fileCsv, converterWriteData_2502.class, new converterDataListener_2502()).sheet().doRead();
    }

    /**
     * 检查本地时间格式，在时间长度缺失时是否可以指定时间格式
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocalFormat() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setDate(DateUtils.parseDate("2019/12/05 12:02:08","yyyy/MM/dd HH:mm:ss"));
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2001-8-1 17:2", "yyyy-MM-dd HH:mm","SST"));
        LocalDateTime t=LocalDateTime.parse("2001-08-01T20:02");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d= sf.parse("2019-12-5 12:02:08");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        Assert.assertEquals(converterWriteData.getDate(), d);
        System.out.println("Date is "+converterWriteData.getDate());
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }

    /**
     * 检查时间长度缺失时是否可以不指定时间格式
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocalFormat_null() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setDate(DateUtils.parseDate("2019/7/1","yyyy/MM/dd"));
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2011-12-7 23:08:1", "yyyy-MM-dd HH:mm:ss",null));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d= sf.parse("2019-7-1");
        LocalDateTime t=LocalDateTime.parse("2011-12-07T23:08:01");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        Assert.assertEquals(converterWriteData.getDate(), d);
        System.out.println("Date is "+converterWriteData.getDate());
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }

    /**
     * 检查地区和格式缺失情况
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocalFormat_nullfor() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setDate(DateUtils.parseDate("1999-07-17",null));
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2011-11-08 23:01:03", null,null));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d= sf.parse("1999-7-17");
        LocalDateTime t=LocalDateTime.parse("2011-11-08T23:01:03");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        Assert.assertEquals(converterWriteData.getDate(), d);
        System.out.println("Date is "+converterWriteData.getDate());
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }

    /**
     * 不在文件中进行，检查AET时区（澳洲时间）
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocal_timezone1() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2011-11-08 23:01:03", null,"AET"));
        LocalDateTime t=LocalDateTime.parse("2011-11-09T02:01:03");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }

    /**
     * 不在文件中进行，检查巴基斯坦卡拉奇
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocal_timezone2() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2001-01-18 16:00:43", null,"PLT"));
        LocalDateTime t=LocalDateTime.parse("2001-01-18T13:00:43");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }

    /**
     * 不在文件中，检查津巴布韦-哈拉雷
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocal_timezone3() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2020-02-29 01:00:43", null,"CAT"));
        LocalDateTime t=LocalDateTime.parse("2020-02-28T19:00:43");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }

    /**
     * 不在文件中，检查美国圣保罗时区，以及闰年跨月。
     *
     * @throws Exception 异常
     */
    @Test
    public void checkLocal_timezone4() throws Exception {
        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2020-03-01 01:00:43", null,"BET"));
        LocalDateTime t=LocalDateTime.parse("2020-02-29T14:00:43");
        Assert.assertEquals(converterWriteData.getLocalDateTime(), t);
        System.out.println("Local Date is "+converterWriteData.getLocalDateTime());
    }



//    private void readAndWrite(File file,String place) throws Exception {
//        EasyExcel.write(file, converterWriteData_2502.class).sheet().doWrite(data(place));
//        EasyExcel.read(file, converterWriteData_2502.class, new converterDataListener_2502()).sheet().doRead();
//    }
//    private List<converterWriteData_2502> data(String place) throws Exception {
//        List<converterWriteData_2502> list = new ArrayList<converterWriteData_2502>();
//        converterWriteData_2502 converterWriteData = new converterWriteData_2502();
//        converterWriteData.setDate(DateUtils.parseDate("2020-01-01 1:01:01","yyyy-MM-dd HH:mm:ss"));
//        converterWriteData.setLocalDateTime(DateUtils.parseLocalDateTimeWithZoneID("2020-01-01 01:01:01", "yyyy-MM-dd HH:mm:ss",place));
//        list.add(converterWriteData);
//        return list;
//    }
}
