package com.alibaba.easyexcel.test.core.kvformat;

import com.alibaba.excel.exception.ExcelCommonException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author xiajiafu
 * @since 2022/7/13
 */
@Getter
@AllArgsConstructor
public enum SexTypeEnum {

    MALE("male", "男"),

    FEMALE("female", "女"),
    ;

    final String key;
    final String desc;

    public static String getKeyByDesc(String desc) {
        return Arrays.stream(values()).filter(o -> o.getDesc().equals(desc)).findFirst()
            .map(SexTypeEnum::getKey).orElseThrow(() -> new ExcelCommonException("sex type not exists！"));
    }

    public static String getDescByKey(String key) {
        return Arrays.stream(values()).filter(o -> o.getKey().equals(key)).findFirst()
            .map(SexTypeEnum::getDesc).orElseThrow(() -> new ExcelCommonException("sex type not exists！"));
    }

}
