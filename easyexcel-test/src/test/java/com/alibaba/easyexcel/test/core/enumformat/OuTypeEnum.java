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
public enum OuTypeEnum {


    UNIT_1(1, "类型1"),
    UNIT_2(2, "类型2"),
    ;

    final Integer key;
    final String desc;

    public static String getDescByKey(Integer key) {
        return Arrays.stream(values()).filter(o -> o.getKey().equals(key)).findFirst()
            .map(OuTypeEnum::getDesc).orElse("");
    }
}
