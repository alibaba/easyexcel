package com.alibaba.easytools.base.enums;

import lombok.Getter;

/**
 * 是否枚举
 *
 * @author 是仪
 */
@Getter
public enum YesOrNoEnum implements BaseEnum<String> {

    /**
     * 是
     */
    YES("是", true),
    /**
     * 未读
     */
    NO("否", false),

    ;

    final String description;
    final boolean booleanValue;

    YesOrNoEnum(String description, boolean booleanValue) {
        this.description = description;
        this.booleanValue = booleanValue;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    /**
     * 根据布尔值转换
     *
     * @param booleanValue 布尔值
     * @return
     */
    public static YesOrNoEnum valueOf(Boolean booleanValue) {
        if (booleanValue == null) {
            return null;
        }
        if (booleanValue) {
            return YesOrNoEnum.YES;
        }
        return YesOrNoEnum.NO;
    }

}
