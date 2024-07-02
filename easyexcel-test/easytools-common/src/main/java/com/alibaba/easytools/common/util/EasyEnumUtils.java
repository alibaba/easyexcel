package com.alibaba.easytools.common.util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.easytools.base.enums.BaseEnum;

/**
 * enum工具类
 * <p>
 * 主要为了解决每个枚举都需要写一个根据code 获取value 的函数，看起来不太友好
 *
 * @author Jiaju Zhuang
 */
public class EasyEnumUtils {
    /**
     * 枚举缓存 不用每次都去循环读取枚举
     */
    private static final Map<Object, Map<?, BaseEnum<?>>> ENUM_CACHE = new ConcurrentHashMap<>();

    /**
     * 根据一个枚举类型获取 枚举的描述
     *
     * @param clazz 枚举的class
     * @param code  枚举的编码
     * @param <T>   枚举的类型
     * @return 找不到code 则返回为空
     */
    public static <T extends BaseEnum<?>> String getDescription(final Class<T> clazz, final Object code) {
        BaseEnum<?> baseEnum = getEnum(clazz, code);
        if (baseEnum == null) {
            return null;
        }
        return baseEnum.getDescription();
    }

    /**
     * 根据一个枚举类型获取 枚举的描述
     *
     * @param clazz 枚举的class
     * @param code  枚举的编码
     * @param <T>   枚举的类型
     * @return 找不到code 则返回为空
     */
    public static <T extends BaseEnum<?>> T getEnum(final Class<T> clazz, final Object code) {
        return getEnumMap(clazz).get(code);
    }

    /**
     * 校验是否是一个有效的枚举
     *
     * @param clazz 枚举的class
     * @param code  枚举的编码 , null 也认为是一个有效的枚举
     * @param <T>   枚举的类型
     * @return 是否有效
     */
    public static <T extends BaseEnum<?>> boolean isValidEnum(final Class<T> clazz, final Object code) {
        return isValidEnum(clazz, code, true);
    }

    /**
     * 校验是否是一个有效的枚举
     *
     * @param clazz      枚举的class
     * @param code       枚举的编码,为空认为是一个无效的枚举
     * @param ignoreNull 是否忽略空的code
     * @param <T>        枚举的类型
     * @return 是否有效
     */
    public static <T extends BaseEnum<?>> boolean isValidEnum(final Class<T> clazz, final Object code,
        final boolean ignoreNull) {
        if (code == null) {
            return ignoreNull;
        }
        return getEnumMap(clazz).containsKey(code);
    }

    /**
     * 获取一个枚举的code  Enum的map
     *
     * @param clazz 枚举的class
     * @param <T>   枚举的类型
     * @return Map<code, Enum>
     */
    public static <T extends BaseEnum<?>> Map<Object, T> getEnumMap(final Class<T> clazz) {
        String className = clazz.getName();
        Map<?, BaseEnum<?>> result = ENUM_CACHE.computeIfAbsent(className, value -> {
            T[] baseEnums = clazz.getEnumConstants();
            return Arrays.stream(baseEnums)
                .collect(Collectors.toMap(BaseEnum::getCode, Function.identity()));
        });
        return (Map)result;
    }
}
