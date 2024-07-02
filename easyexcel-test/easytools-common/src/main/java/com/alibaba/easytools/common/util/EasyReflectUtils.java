package com.alibaba.easytools.common.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射工具类
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class EasyReflectUtils {

    /**
     * 获取一个类的父类的泛型类型，如果没有 则获取父类的父类
     *
     * @return
     */
    public static Class<?> superParameterizedType(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        // 获取父类的
        if (type instanceof Class) {
            type = ((Class<?>)type).getGenericSuperclass();
        }
        // 获取父类的父类
        if (type instanceof Class) {
            type = ((Class<?>)type).getGenericSuperclass();
        }

        // 不管父类的父类是不是 强制转 如果不是这里会报错
        ParameterizedType parameterizedType = (ParameterizedType)type;
        // 直接拿第一个强制转换
        return (Class<?>)parameterizedType.getActualTypeArguments()[0];
    }

}
