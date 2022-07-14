package com.alibaba.easyexcel.test.core.enumformat;

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

    public static String getDescByKey(String key) {
        return Arrays.stream(values()).filter(o -> o.getKey().equals(key)).findFirst()
            .map(SexTypeEnum::getDesc).orElse("");
    }

}
