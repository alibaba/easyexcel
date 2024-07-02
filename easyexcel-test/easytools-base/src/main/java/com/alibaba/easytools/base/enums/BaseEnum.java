package com.alibaba.easytools.base.enums;

/**
 * 基础的枚举
 *
 * 由于java枚举继承的限制，枚举基类只能设计为接口，请自行保证子类一定是枚举类型。
 *
 * @author Jiaju Zhuang
 **/
public interface BaseEnum<T> {

    /**
     * 返回枚举的code。一般建议直接返回枚举的name
     *
     * @return code
     */
    T getCode();

    /**
     * 返回枚举的描述。返回枚举的中文 方便前端下拉
     *
     * @return description
     */
    String getDescription();

}
