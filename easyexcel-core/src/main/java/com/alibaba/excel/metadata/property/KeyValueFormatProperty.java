package com.alibaba.excel.metadata.property;

import com.alibaba.excel.annotation.format.KeyValueFormat;
import com.alibaba.excel.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiajiafu
 * @since 2022/7/13
 */
@Getter
@Setter
@EqualsAndHashCode
public class KeyValueFormatProperty {

    private Class<?> targetClass;
    private String javaify;
    private String excelify;

    public KeyValueFormatProperty(Class<?> targetClass, String javaify, String excelify) {
        this.targetClass = targetClass;
        this.javaify = javaify;
        this.excelify = excelify;
    }

    public static KeyValueFormatProperty build(KeyValueFormat keyValueFormat) {
        if (keyValueFormat == null) {
            return null;
        }
        if (StringUtils.isBlank(keyValueFormat.javaify()) && StringUtils.isBlank(keyValueFormat.excelify())) {
            return null;
        }
        return new KeyValueFormatProperty(keyValueFormat.targetClass(), keyValueFormat.javaify(),
            keyValueFormat.excelify());
    }

}
