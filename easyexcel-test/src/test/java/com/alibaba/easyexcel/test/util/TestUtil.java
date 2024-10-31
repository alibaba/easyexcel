package com.alibaba.easyexcel.test.util;

import com.alibaba.excel.util.DateUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * test util
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class TestUtil {

    public static final Date TEST_DATE;
    public static final LocalTime TEST_LOCAL_TIME = LocalTime.of(12, 0, 0);
    public static final LocalDate TEST_LOCAL_DATE = LocalDate.of(2020, 1, 1);
    public static final LocalDateTime TEST_LOCAL_DATE_TIME = LocalDateTime.of(2020, 1, 1, 1, 1, 1);

    static {
        try {
            TEST_DATE = DateUtils.parseDate("2020-01-01 01:01:01");
        } catch (ParseException e) {
            log.error("init TestUtil error.", e);
            throw new RuntimeException(e);
        }
    }

}
