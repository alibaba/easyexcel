package com.alibaba.atasuper.api.demo.enums;

import com.alibaba.easytools.base.enums.BaseEnum;

import lombok.Getter;

/**
 * TODO 示例状态枚举
 *
 * @author 是仪
 */
@Getter
public enum DemoStatusEnum implements BaseEnum<String> {

    /**
     * 草稿
     */
    DRAFT("草稿"),

    /**
     * 线上
     */
    RELEASE("线上"),
    ;

    final String description;

    DemoStatusEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
