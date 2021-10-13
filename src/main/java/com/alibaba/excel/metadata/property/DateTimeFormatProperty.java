package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.util.BooleanUtils;

import lombok.Data;

/**
 * Configuration from annotations
 *
 * @author Jiaju Zhuang
 */
@Data
public class DateTimeFormatProperty {
    private String format;
    private Boolean use1904windowing;

    public DateTimeFormatProperty(String format, Boolean use1904windowing) {
        this.format = format;
        this.use1904windowing = use1904windowing;
    }

    public static DateTimeFormatProperty build(DateTimeFormat dateTimeFormat) {
        if (dateTimeFormat == null) {
            return null;
        }
        return new DateTimeFormatProperty(dateTimeFormat.value(),
            BooleanUtils.isTrue(dateTimeFormat.use1904windowing().getBooleanValue()));
    }
}
