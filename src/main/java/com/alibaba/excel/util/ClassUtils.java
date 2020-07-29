package com.alibaba.excel.util;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.event.Handler;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

/**
 * Class utils
 *
 * @author Jiaju Zhuang
 **/
public class ClassUtils {

    private static final Map<Class, SoftReference<FieldCache>> FIELD_CACHE =
        new ConcurrentHashMap<Class, SoftReference<FieldCache>>();

    public static void declaredFields(Class clazz, Map<Integer, Field> sortedAllFiledMap,
        Map<Integer, Field> indexFiledMap, Map<String, Field> ignoreMap, Boolean convertAllFiled,
        Boolean needIgnore, Holder holder) {
        FieldCache fieldCache = getFieldCache(clazz, convertAllFiled);
        if (fieldCache == null) {
            return;
        }
        if (ignoreMap != null) {
            ignoreMap.putAll(fieldCache.getIgnoreMap());
        }
        Map<Integer, Field> tempIndexFildMap = indexFiledMap;
        if (tempIndexFildMap == null) {
            tempIndexFildMap = new TreeMap<Integer, Field>();
        }
        tempIndexFildMap.putAll(fieldCache.getIndexFiledMap());

        if (!needIgnore) {
            sortedAllFiledMap.putAll(fieldCache.getSortedAllFiledMap());
            return;
        }

        int index = 0;
        for (Map.Entry<Integer, Field> entry : fieldCache.getSortedAllFiledMap().entrySet()) {
            Field field = entry.getValue();
            if (((WriteHolder) holder).ignore(entry.getValue().getName(), entry.getKey())) {
                if (ignoreMap != null) {
                    ignoreMap.put(field.getName(), field);
                }
                while (tempIndexFildMap.containsKey(index)) {
                    tempIndexFildMap.remove(index);
                    index++;
                }
            } else {
                sortedAllFiledMap.put(index, field);
                index++;
            }
        }
    }

    public static void declaredFields(Class clazz, Map<Integer, Field> sortedAllFiledMap, Boolean convertAllFiled,
        Boolean needIgnore, WriteHolder writeHolder) {
        declaredFields(clazz, sortedAllFiledMap, null, null, convertAllFiled, needIgnore, writeHolder);
    }

    private static FieldCache getFieldCache(Class clazz, Boolean convertAllFiled) {
        if (clazz == null) {
            return null;
        }
        SoftReference<FieldCache> fieldCacheSoftReference = FIELD_CACHE.get(clazz);
        if (fieldCacheSoftReference != null && fieldCacheSoftReference.get() != null) {
            return fieldCacheSoftReference.get();
        }
        synchronized (clazz) {
            fieldCacheSoftReference = FIELD_CACHE.get(clazz);
            if (fieldCacheSoftReference != null && fieldCacheSoftReference.get() != null) {
                return fieldCacheSoftReference.get();
            }
            declaredFields(clazz, convertAllFiled);
        }
        return FIELD_CACHE.get(clazz).get();
    }

    private static void declaredFields(Class clazz, Boolean convertAllFiled) {
        List<Field> tempFieldList = new ArrayList<Field>();
        Class tempClass = clazz;
        // When the parent class is null, it indicates that the parent class (Object class) has reached the top
        // level.
        while (tempClass != null && tempClass != BaseRowModel.class) {
            Collections.addAll(tempFieldList, tempClass.getDeclaredFields());
            // Get the parent class and give it to yourself
            tempClass = tempClass.getSuperclass();
        }
        // Screening of field
        Map<Integer, List<Field>> orderFiledMap = new TreeMap<Integer, List<Field>>();
        Map<Integer, Field> indexFiledMap = new TreeMap<Integer, Field>();
        Map<String, Field> ignoreMap = new HashMap<String, Field>(16);

        ExcelIgnoreUnannotated excelIgnoreUnannotated =
            (ExcelIgnoreUnannotated) clazz.getAnnotation(ExcelIgnoreUnannotated.class);
        for (Field field : tempFieldList) {
            declaredOneField(field, orderFiledMap, indexFiledMap, ignoreMap, excelIgnoreUnannotated, convertAllFiled);
        }
        FIELD_CACHE.put(clazz, new SoftReference<FieldCache>(
            new FieldCache(buildSortedAllFiledMap(orderFiledMap, indexFiledMap), indexFiledMap, ignoreMap)));
    }

    private static Map<Integer, Field> buildSortedAllFiledMap(Map<Integer, List<Field>> orderFiledMap,
        Map<Integer, Field> indexFiledMap) {

        Map<Integer, Field> sortedAllFiledMap = new HashMap<Integer, Field>(
            (orderFiledMap.size() + indexFiledMap.size()) * 4 / 3 + 1);

        Map<Integer, Field> tempIndexFiledMap = new HashMap<Integer, Field>(indexFiledMap);
        int index = 0;
        for (List<Field> fieldList : orderFiledMap.values()) {
            for (Field field : fieldList) {
                while (tempIndexFiledMap.containsKey(index)) {
                    sortedAllFiledMap.put(index, tempIndexFiledMap.get(index));
                    tempIndexFiledMap.remove(index);
                    index++;
                }
                sortedAllFiledMap.put(index, field);
                index++;
            }
        }
        sortedAllFiledMap.putAll(tempIndexFiledMap);
        return sortedAllFiledMap;
    }

    private static void declaredOneField(Field field, Map<Integer, List<Field>> orderFiledMap,
        Map<Integer, Field> indexFiledMap, Map<String, Field> ignoreMap, ExcelIgnoreUnannotated excelIgnoreUnannotated,
        Boolean convertAllFiled) {
        ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
        if (excelIgnore != null) {
            ignoreMap.put(field.getName(), field);
            return;
        }
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        boolean noExcelProperty = excelProperty == null
            && ((convertAllFiled != null && !convertAllFiled) || excelIgnoreUnannotated != null);
        if (noExcelProperty) {
            ignoreMap.put(field.getName(), field);
            return;
        }
        boolean isStaticFinalOrTransient =
            (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
                || Modifier.isTransient(field.getModifiers());
        if (excelProperty == null && isStaticFinalOrTransient) {
            ignoreMap.put(field.getName(), field);
            return;
        }
        if (excelProperty != null && excelProperty.index() >= 0) {
            if (indexFiledMap.containsKey(excelProperty.index())) {
                throw new ExcelCommonException("The index of '" + indexFiledMap.get(excelProperty.index()).getName()
                    + "' and '" + field.getName() + "' must be inconsistent");
            }
            indexFiledMap.put(excelProperty.index(), field);
            return;
        }

        int order = Integer.MAX_VALUE;
        if (excelProperty != null) {
            order = excelProperty.order();
        }
        List<Field> orderFiledList = orderFiledMap.get(order);
        if (orderFiledList == null) {
            orderFiledList = new ArrayList<Field>();
            orderFiledMap.put(order, orderFiledList);
        }
        orderFiledList.add(field);
    }

    private static class FieldCache {

        private Map<Integer, Field> sortedAllFiledMap;
        private Map<Integer, Field> indexFiledMap;
        private Map<String, Field> ignoreMap;

        public FieldCache(Map<Integer, Field> sortedAllFiledMap, Map<Integer, Field> indexFiledMap,
            Map<String, Field> ignoreMap) {
            this.sortedAllFiledMap = sortedAllFiledMap;
            this.indexFiledMap = indexFiledMap;
            this.ignoreMap = ignoreMap;
        }

        public Map<Integer, Field> getSortedAllFiledMap() {
            return sortedAllFiledMap;
        }

        public Map<Integer, Field> getIndexFiledMap() {
            return indexFiledMap;
        }

        public Map<String, Field> getIgnoreMap() {
            return ignoreMap;
        }
    }
}
