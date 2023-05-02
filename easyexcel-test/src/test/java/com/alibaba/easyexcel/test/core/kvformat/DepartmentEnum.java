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
public enum DepartmentEnum {

    DEPT_1(1, "产品部"),
    DEPT_2(2, "大后端"),
    DEPT_3(3, "设计部"),
    DEPT_4(4, "大前端"),
    DEPT_5(5, "运营部"),
    ;

    final Integer key;
    final String desc;

    public static Integer getKeyByDesc(String desc) {
        return Arrays.stream(values()).filter(o -> o.getDesc().equals(desc)).findFirst()
            .map(DepartmentEnum::getKey).orElseThrow(() -> new ExcelCommonException("department not exists！"));
    }

    public static String getDescByKey(Integer key) {
        return Arrays.stream(values()).filter(o -> o.getKey().equals(key)).findFirst()
            .map(DepartmentEnum::getDesc).orElseThrow(() -> new ExcelCommonException("department not exists！"));
    }
}
