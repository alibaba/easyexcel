package com.alibaba.easyexcel.test.util;


import com.alibaba.excel.util.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_10;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_14;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_16;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_16_FORWARD_SLASH;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_17;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_19;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_19_FORWARD_SLASH;
import static com.alibaba.excel.util.DateUtils.DATE_FORMAT_SIMPLE_SLASH;
/**
 * @author lonecloud
 */
public class DateUtilTest {

    LocalDateTime testTime = LocalDateTime.of(2023, 1, 1, 1, 1, 1);

    LocalDateTime testTime2 = LocalDateTime.of(2023, 10, 1, 1, 1, 1);
    LocalDateTime testTime3 = LocalDateTime.of(2023, 1, 10, 1, 1, 1);

    public String[] patternTest = {DATE_FORMAT_10, DATE_FORMAT_14, DATE_FORMAT_16, DATE_FORMAT_16_FORWARD_SLASH, DATE_FORMAT_17, DATE_FORMAT_19, DATE_FORMAT_19_FORWARD_SLASH, DATE_FORMAT_SIMPLE_SLASH};

    @Test
    public void switchDateFormatTest() {
        for (String pattern : patternTest) {
            String format = testTime.format(DateTimeFormatter.ofPattern(pattern));
            Assertions.assertEquals(pattern, DateUtils.switchDateFormat(format));
        }
        for (String pattern : patternTest) {
            String format = testTime2.format(DateTimeFormatter.ofPattern(pattern));
            Assertions.assertEquals(pattern, DateUtils.switchDateFormat(format));
        }
        for (String pattern : patternTest) {
            String format = testTime3.format(DateTimeFormatter.ofPattern(pattern));
            Assertions.assertEquals(pattern, DateUtils.switchDateFormat(format));
        }
    }
}
