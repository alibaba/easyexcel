package com.alibaba.easytools.base.enums;

import lombok.Getter;

/**
 * 操作枚举
 *
 * @author 是仪
 */
@Getter
public enum OperationEnum implements BaseEnum<String> {
    /**
     * 新增
     */
    CREATE("新增"),

    /**
     * 修改
     */
    UPDATE("修改"),

    /**
     * 删除
     */
    DELETE("删除"),

    ;

    final String description;

    OperationEnum(String description) {
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
