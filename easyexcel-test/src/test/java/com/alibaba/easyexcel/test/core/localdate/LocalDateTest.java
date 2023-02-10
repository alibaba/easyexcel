package com.alibaba.easyexcel.test.core.localdate;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;

/**
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalDateTest {

	private static final Logger log = LoggerFactory.getLogger(LocalDateTest.class);
	private static File file1;
	private static File file2;
	private static File file3;

	@BeforeClass
	public static void init() {
		file1 = TestFileUtil.readFile("localdate/local-date-time.xlsx");
		file2 = TestFileUtil.readFile("localdate/local-date-time2.xlsx");
		file3 = TestFileUtil.readFile("localdate/local-date-time-fill.xlsx");
	}

	// 读取
	@Test
	public void read() throws Exception {
//		String fileName = TestFileUtil.getPath() + "localdate/local-date-time.xlsx";
		EntryListener entryListener = new EntryListener();
		EasyExcel.read(file1, DataEntry.class, entryListener).sheet().doRead();
		log.info("读取到的数据==>{}", JSON.toJSONString(entryListener.getEntries()));
	}

	// 读取
	@Test
	public void read2() throws Exception {
//		String fileName = TestFileUtil.getPath() + "localdate/local-date-time2.xlsx";
		EntryListener entryListener = new EntryListener();
		EasyExcel.read(file2, DataEntry.class, entryListener).sheet().doRead();
		log.info("读取到的数据==>{}", JSON.toJSONString(entryListener.getEntries()));
	}

	// 写
	@Test
	public void write() throws Exception {

		List<DataEntry> entries = buildData();

		String fileName = TestFileUtil.getPath() + "local-date-time-" + System.currentTimeMillis() + ".xlsx";

		EasyExcel.write(fileName, DataEntry.class).sheet("demo").doWrite(entries);
	}

	// 填充
	@Test
	public void fill() throws Exception {
		List<DataEntry> entries = buildData();
//		String templateFileName = TestFileUtil.getPath() + "localdate/local-date-time-fill.xlsx";

		String fileName = TestFileUtil.getPath() + "local-date-time-fill-" + System.currentTimeMillis() + ".xlsx";

		EasyExcel.write(fileName).withTemplate(file3).sheet().doFill(entries);
	}

	private List<DataEntry> buildData() {
		List<DataEntry> entries = ListUtils.newArrayList();
		BigDecimal hundred = new BigDecimal(1000);
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDate localDate = LocalDate.now();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		for (int i = 1; i < 11; i++) {
			calendar.setTime(date);

			DataEntry item = new DataEntry();
			item.setUserName("王麻子" + i);
			item.setAge(i * 10);
			item.setHashChild(i % 2 > 0);
			item.setIncome(hundred.multiply(new BigDecimal(i)));
			item.setHeight(i % 2 > 0 ? 1.72 : 1.68);
			item.setWeight(i % 2 > 0 ? 50.00 : 40.00);
			item.setBirthdayTime(localDateTime.plusDays(i));
			item.setBirthday(localDate.plusDays(i));

			calendar.add(Calendar.DAY_OF_MONTH, i);
			item.setCreateDate(calendar.getTime());

			calendar.add(Calendar.HOUR_OF_DAY, i);
			item.setModifyDate(calendar.getTime());
			entries.add(item);
		}
		return entries;
	}

}
