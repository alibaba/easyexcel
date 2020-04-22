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
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * Class utils
 *
 * @author Jiaju Zhuang
 **/
public class ClassUtils {
    private static final Map<Class, SoftReference<FieldCache>> FIELD_CACHE =
        new ConcurrentHashMap<Class, SoftReference<FieldCache>>();

    public static void declaredFields(Class clazz, List<Field> defaultFieldList, Map<Integer, Field> customFiledMap,
        Map<String, Field> ignoreMap, Boolean convertAllFiled) {
        FieldCache fieldCache = getFieldCache(clazz, convertAllFiled);
        if (fieldCache != null) {
            defaultFieldList.addAll(fieldCache.getDefaultFieldList());
            customFiledMap.putAll(fieldCache.getCustomFiledMap());
            ignoreMap.putAll(fieldCache.getIgnoreMap());
        }
    }

    public static void declaredFields(Class clazz, List<Field> fieldList, Boolean convertAllFiled) {
        FieldCache fieldCache = getFieldCache(clazz, convertAllFiled);
        if (fieldCache != null) {
            fieldList.addAll(fieldCache.getAllFieldList());
        }
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
        List<Field> defaultFieldList = new ArrayList<Field>();
        Map<Integer, Field> customFiledMap = new TreeMap<Integer, Field>();
        List<Field> allFieldList = new ArrayList<Field>();
        Map<String, Field> ignoreMap = new HashMap<String, Field>(16);

        ExcelIgnoreUnannotated excelIgnoreUnannotated =
            (ExcelIgnoreUnannotated)clazz.getAnnotation(ExcelIgnoreUnannotated.class);
        for (Field field : tempFieldList) {
            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if (excelIgnore != null) {
                ignoreMap.put(field.getName(), field);
                continue;
            }
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            boolean noExcelProperty = excelProperty == null
                && ((convertAllFiled != null && !convertAllFiled) || excelIgnoreUnannotated != null);
            if (noExcelProperty) {
                ignoreMap.put(field.getName(), field);
                continue;
            }
            boolean isStaticFinalOrTransient =
                (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
                    || Modifier.isTransient(field.getModifiers());
            if (excelProperty == null && isStaticFinalOrTransient) {
                ignoreMap.put(field.getName(), field);
                continue;
            }
            if (excelProperty == null || excelProperty.index() < 0) {
                defaultFieldList.add(field);
                allFieldList.add(field);
                continue;
            }
            if (customFiledMap.containsKey(excelProperty.index())) {
                throw new ExcelCommonException("The index of '" + customFiledMap.get(excelProperty.index()).getName()
                    + "' and '" + field.getName() + "' must be inconsistent");
            }
            customFiledMap.put(excelProperty.index(), field);
        }

        List<Field> allWriteFieldList = new ArrayList<Field>(customFiledMap.values());
        allWriteFieldList.addAll(allFieldList);
        FIELD_CACHE.put(clazz,
            new SoftReference<FieldCache>(new FieldCache(defaultFieldList, customFiledMap, allWriteFieldList, ignoreMap)));
    }

    private static class FieldCache {
        private List<Field> defaultFieldList;
        private Map<Integer, Field> customFiledMap;
        private List<Field> allFieldList;
        private Map<String, Field> ignoreMap;

        public FieldCache(List<Field> defaultFieldList, Map<Integer, Field> customFiledMap, List<Field> allFieldList,
            Map<String, Field> ignoreMap) {
            this.defaultFieldList = defaultFieldList;
            this.customFiledMap = customFiledMap;
            this.allFieldList = allFieldList;
            this.ignoreMap = ignoreMap;
        }

        public List<Field> getDefaultFieldList() {
            return defaultFieldList;
        }

        public Map<Integer, Field> getCustomFiledMap() {
            return customFiledMap;
        }

        public List<Field> getAllFieldList() {
            return allFieldList;
        }

        public Map<String, Field> getIgnoreMap() {
            return ignoreMap;
        }

    }
}
