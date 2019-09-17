package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.format.DateTimeFormat;

/**
 * Configuration from annotations
 *
 * @author Jiaju Zhuang
 */
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
        return new DateTimeFormatProperty(dateTimeFormat.value(), dateTimeFormat.use1904windowing());
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Boolean getUse1904windowing() {
        return use1904windowing;
    }

    public void setUse1904windowing(Boolean use1904windowing) {
        this.use1904windowing = use1904windowing;
    }
}
