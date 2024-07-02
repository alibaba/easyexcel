package com.alibaba.easytools.common.util;

import java.util.Optional;
import java.util.function.Function;

/**
 * Optional的工具类
 *
 * @author Jiaju Zhuang
 */
public class EasyOptionalUtils {

    /**
     * 将一个可能未null 的对象 获取其值
     *
     * @param source   原始对象
     * @param function 转换方法
     * @param <T>
     * @param <R>
     * @return 返回值 为空则返回nulll
     */
    public static <T, R> R mapTo(T source, Function<T, R> function) {
        return mapTo(source, function, null);
    }

    /**
     * 将一个可能未null 的对象 获取其值
     *
     * @param source       原始对象
     * @param function     转换方法
     * @param defaultValue 默认值
     * @param <T>
     * @param <R>
     * @return 返回值
     */
    public static <T, R> R mapTo(T source, Function<T, R> function, R defaultValue) {
        return Optional.ofNullable(source).map(function).orElse(defaultValue);
    }
}
