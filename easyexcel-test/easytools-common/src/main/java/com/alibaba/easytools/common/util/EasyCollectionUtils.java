package com.alibaba.easytools.common.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 集合工具类
 *
 * @author Jiaju Zhuang
 */
public class EasyCollectionUtils {

    /**
     * 集合stream
     *
     * @param collection 集合
     * @param <T>        返回的类型
     * @return 集合的stream
     */
    public static <T> Stream<T> stream(Collection<T> collection) {
        return collection != null ? collection.stream() : Stream.empty();
    }

    /**
     * for 循环一个集合
     *
     * @param collection
     * @param action
     * @param <T>
     */
    public static <T> void forEach(Collection<T> collection, Consumer<? super T> action) {
        if (collection == null || collection.isEmpty()) {
            return;
        }
        collection.forEach(action);
    }

    /**
     * 将一个集合 转换成一个list
     * <p>
     * 会过滤集合中转换前后的空数据，所以会入参出参数量不一致
     *
     * @param collection 集合
     * @param function   转换function
     * @param <T>        转换前的数据类型
     * @param <R>        转换后数据类型
     * @return list 如果入参为空 会返回一个空数组，且无法修改
     */
    public static <T, R> List<R> toList(Collection<T> collection, Function<T, R> function) {
        return stream(collection)
            .filter(Objects::nonNull)
            .map(function)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 将一个集合 转换成一个list
     * <p>
     * 会过滤集合中转换前后的空数据，所以会入参出参数量不一致
     *
     * @param collection 集合
     * @param function   转换function
     * @param <T>        转换前的数据类型
     * @param <R>        转换后数据类型
     * @return list 如果入参为空 会返回一个空数组，且无法修改
     */
    public static <T, R> List<R> toList(Collection<T> collection, Function<T, R> function, Predicate<T> filter) {
        return stream(collection)
            .filter(Objects::nonNull)
            .filter(filter)
            .map(function)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 将一个集合 转换成一个list 并去重
     * <p>
     * 会过滤集合中转换前后的空数据，所以会入参出参数量不一致
     *
     * @param collection 集合
     * @param function   转换function
     * @param <T>        转换前的数据类型
     * @param <R>        转换后数据类型
     * @return list 如果入参为空 会返回一个空数组，且无法修改
     */
    public static <T, R> List<R> toDistinctList(Collection<T> collection, Function<T, R> function) {
        return stream(collection)
            .filter(Objects::nonNull)
            .map(function)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * 将一个集合 里面的List属性的字段全部铺开，重新转换成新的list
     * <p>
     * 会过滤集合中转换前后的空数据，所以会入参出参数量不一致
     *
     * @param collection 集合
     * @param function   转换function
     * @param <T>        转换前的数据类型
     * @param <R>        转换后数据类型
     * @return list 如果入参为空 会返回一个空数组，且无法修改
     */
    public static <T, R> List<R> toFlatList(Collection<T> collection, Function<T, Collection<R>> function) {
        return stream(collection)
            .map(function)
            .filter(CollectionUtils::isNotEmpty)
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 将一个集合 转换成一个set
     * <p>
     * 会过滤集合中转换前后的空数据
     *
     * @param collection 集合
     * @param function   转换function
     * @param <T>        转换前的数据类型
     * @param <R>        转换后数据类型
     * @return list 如果入参为空 会返回一个空数组，且无法修改
     */
    public static <T, R> Set<R> toSet(Collection<T> collection, Function<T, R> function) {
        return stream(collection)
            .filter(Objects::nonNull)
            .map(function)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }

    /**
     * 将一个集合转成map，遇到key冲突以第二个为准
     *
     * @param collection    集合
     * @param keyFunction   keyFunction
     * @param valueFunction valueFunction
     * @param <K>           key数据类型
     * @param <V>           value数据类型
     * @param <T>           转换前的数据类型
     * @return 转成以后的map
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, Function<? super T, K> keyFunction,
        Function<? super T, V> valueFunction) {
        return stream(collection)
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(keyFunction, valueFunction, (oldValue, newValue) -> newValue));
    }

    /**
     * 将一个集合转成map,map的value就是集合的值，遇到key冲突以第二个为准
     *
     * @param collection  集合
     * @param keyFunction keyFunction
     * @param <K>         key数据类型
     * @param <T>         转换前的数据类型
     * @return 转成以后的map
     */
    public static <K, T> Map<K, T> toIdentityMap(Collection<T> collection, Function<? super T, K> keyFunction) {
        return toMap(collection, keyFunction, Function.identity());
    }

    /**
     * 将一个集合转成map 转换成一个 map 数据包含list
     *
     * @param collection  集合
     * @param keyFunction keyFunction
     * @param <K>         key数据类型
     * @param <T>         转换前的数据类型
     * @return 转成以后的map
     */
    public static <K, T> Map<K, List<T>> toGroupingMap(Collection<T> collection, Function<? super T, K> keyFunction) {
        return stream(collection)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(keyFunction));
    }

    /**
     * 往一个集合里面加入另一个一个集合
     *
     * @param collection    原始的集合
     * @param collectionAdd 需要被加入的集合
     * @param <C>
     * @return 是否加入了数据
     */
    public static <C> boolean addAll(final Collection<C> collection, final Collection<C> collectionAdd) {
        if (collectionAdd == null) {
            return false;
        }
        return collection.addAll(collectionAdd);
    }

    /**
     * 判断一个集合的长度为0 但是不为null
     *
     * @param collection 集合
     * @return
     */
    public static boolean isEmptyButNotNull(final Collection<?> collection) {
        return collection != null && collection.isEmpty();
    }

    /**
     * 判断 一堆集合 是否存在一个 长度为0 但是不为null的数组
     *
     * @param collections 为空则返回false
     * @return
     */
    public static boolean isAnyEmptyButNotNull(final Collection<?>... collections) {
        if (ArrayUtils.isEmpty(collections)) {
            return false;
        }
        for (final Collection<?> collection : collections) {
            if (isEmptyButNotNull(collection)) {
                return true;
            }
        }
        return false;
    }
}
