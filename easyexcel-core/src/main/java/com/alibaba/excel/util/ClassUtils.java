package com.alibaba.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.ConfigurationHolder;
import com.alibaba.excel.metadata.FieldCache;
import com.alibaba.excel.metadata.FieldWrapper;
import com.alibaba.excel.metadata.property.DateTimeFormatProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.NumberFormatProperty;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.support.cglib.beans.BeanMap;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Apache Software Foundation (ASF)
 */
public class ClassUtils {

    /**
     * memory cache
     */
    public static final Map<FieldCacheKey, FieldCache> FIELD_CACHE = new ConcurrentHashMap<>();
    /**
     * thread local cache
     */
    private static final ThreadLocal<Map<FieldCacheKey, FieldCache>> FIELD_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * The cache configuration information for each of the class
     */
    public static final ConcurrentHashMap<Class<?>, Map<String, ExcelContentProperty>> CLASS_CONTENT_CACHE
        = new ConcurrentHashMap<>();

    /**
     * The cache configuration information for each of the class
     */
    private static final ThreadLocal<Map<Class<?>, Map<String, ExcelContentProperty>>> CLASS_CONTENT_THREAD_LOCAL
        = new ThreadLocal<>();

    /**
     * The cache configuration information for each of the class
     */
    public static final ConcurrentHashMap<ContentPropertyKey, ExcelContentProperty> CONTENT_CACHE
        = new ConcurrentHashMap<>();

    /**
     * The cache configuration information for each of the class
     */
    private static final ThreadLocal<Map<ContentPropertyKey, ExcelContentProperty>> CONTENT_THREAD_LOCAL
        = new ThreadLocal<>();

    /**
     * Calculate the configuration information for the class
     *
     * @param dataMap
     * @param headClazz
     * @param fieldName
     * @return
     */
    public static ExcelContentProperty declaredExcelContentProperty(Map<?, ?> dataMap, Class<?> headClazz,
        String fieldName,
        ConfigurationHolder configurationHolder) {
        Class<?> clazz = null;
        if (dataMap instanceof BeanMap) {
            Object bean = ((BeanMap)dataMap).getBean();
            if (bean != null) {
                clazz = bean.getClass();
            }
        }
        return getExcelContentProperty(clazz, headClazz, fieldName, configurationHolder);
    }

    private static ExcelContentProperty getExcelContentProperty(Class<?> clazz, Class<?> headClass, String fieldName,
        ConfigurationHolder configurationHolder) {
        switch (configurationHolder.globalConfiguration().getFiledCacheLocation()) {
            case THREAD_LOCAL:
                Map<ContentPropertyKey, ExcelContentProperty> contentCacheMap = CONTENT_THREAD_LOCAL.get();
                if (contentCacheMap == null) {
                    contentCacheMap = MapUtils.newHashMap();
                    CONTENT_THREAD_LOCAL.set(contentCacheMap);
                }
                return contentCacheMap.computeIfAbsent(buildKey(clazz, headClass, fieldName), key -> {
                    return doGetExcelContentProperty(clazz, headClass, fieldName, configurationHolder);
                });
            case MEMORY:
                return CONTENT_CACHE.computeIfAbsent(buildKey(clazz, headClass, fieldName), key -> {
                    return doGetExcelContentProperty(clazz, headClass, fieldName, configurationHolder);
                });
            case NONE:
                return doGetExcelContentProperty(clazz, headClass, fieldName, configurationHolder);
            default:
                throw new UnsupportedOperationException("unsupported enum");
        }
    }

    private static ExcelContentProperty doGetExcelContentProperty(Class<?> clazz, Class<?> headClass,
        String fieldName,
        ConfigurationHolder configurationHolder) {
        ExcelContentProperty excelContentProperty = Optional.ofNullable(
                declaredFieldContentMap(clazz, configurationHolder))
            .map(map -> map.get(fieldName))
            .orElse(null);
        ExcelContentProperty headExcelContentProperty = Optional.ofNullable(
                declaredFieldContentMap(headClass, configurationHolder))
            .map(map -> map.get(fieldName))
            .orElse(null);
        ExcelContentProperty combineExcelContentProperty = new ExcelContentProperty();

        combineExcelContentProperty(combineExcelContentProperty, headExcelContentProperty);
        if (clazz != headClass) {
            combineExcelContentProperty(combineExcelContentProperty, excelContentProperty);
        }
        return combineExcelContentProperty;
    }

    public static void combineExcelContentProperty(ExcelContentProperty combineExcelContentProperty,
        ExcelContentProperty excelContentProperty) {
        if (excelContentProperty == null) {
            return;
        }
        if (excelContentProperty.getField() != null) {
            combineExcelContentProperty.setField(excelContentProperty.getField());
        }
        if (excelContentProperty.getConverter() != null) {
            combineExcelContentProperty.setConverter(excelContentProperty.getConverter());
        }
        if (excelContentProperty.getDateTimeFormatProperty() != null) {
            combineExcelContentProperty.setDateTimeFormatProperty(excelContentProperty.getDateTimeFormatProperty());
        }
        if (excelContentProperty.getNumberFormatProperty() != null) {
            combineExcelContentProperty.setNumberFormatProperty(excelContentProperty.getNumberFormatProperty());
        }
        if (excelContentProperty.getContentStyleProperty() != null) {
            combineExcelContentProperty.setContentStyleProperty(excelContentProperty.getContentStyleProperty());
        }
        if (excelContentProperty.getContentFontProperty() != null) {
            combineExcelContentProperty.setContentFontProperty(excelContentProperty.getContentFontProperty());
        }
    }

    private static ContentPropertyKey buildKey(Class<?> clazz, Class<?> headClass, String fieldName) {
        return new ContentPropertyKey(clazz, headClass, fieldName);
    }

    private static Map<String, ExcelContentProperty> declaredFieldContentMap(Class<?> clazz,
        ConfigurationHolder configurationHolder) {
        if (clazz == null) {
            return null;
        }
        switch (configurationHolder.globalConfiguration().getFiledCacheLocation()) {
            case THREAD_LOCAL:
                Map<Class<?>, Map<String, ExcelContentProperty>> classContentCacheMap
                    = CLASS_CONTENT_THREAD_LOCAL.get();
                if (classContentCacheMap == null) {
                    classContentCacheMap = MapUtils.newHashMap();
                    CLASS_CONTENT_THREAD_LOCAL.set(classContentCacheMap);
                }
                return classContentCacheMap.computeIfAbsent(clazz, key -> {
                    return doDeclaredFieldContentMap(clazz);
                });
            case MEMORY:
                return CLASS_CONTENT_CACHE.computeIfAbsent(clazz, key -> {
                    return doDeclaredFieldContentMap(clazz);
                });
            case NONE:
                return doDeclaredFieldContentMap(clazz);
            default:
                throw new UnsupportedOperationException("unsupported enum");
        }

    }

    private static Map<String, ExcelContentProperty> doDeclaredFieldContentMap(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        List<Field> tempFieldList = new ArrayList<>();
        Class<?> tempClass = clazz;
        while (tempClass != null) {
            Collections.addAll(tempFieldList, tempClass.getDeclaredFields());
            // Get the parent class and give it to yourself
            tempClass = tempClass.getSuperclass();
        }

        ContentStyle parentContentStyle = clazz.getAnnotation(ContentStyle.class);
        ContentFontStyle parentContentFontStyle = clazz.getAnnotation(ContentFontStyle.class);
        Map<String, ExcelContentProperty> fieldContentMap = MapUtils.newHashMapWithExpectedSize(
            tempFieldList.size());
        for (Field field : tempFieldList) {
            ExcelContentProperty excelContentProperty = new ExcelContentProperty();
            excelContentProperty.setField(field);

            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                Class<? extends Converter<?>> convertClazz = excelProperty.converter();
                if (convertClazz != AutoConverter.class) {
                    try {
                        Converter<?> converter = convertClazz.getDeclaredConstructor().newInstance();
                        excelContentProperty.setConverter(converter);
                    } catch (Exception e) {
                        throw new ExcelCommonException(
                            "Can not instance custom converter:" + convertClazz.getName());
                    }
                }
            }

            ContentStyle contentStyle = field.getAnnotation(ContentStyle.class);
            if (contentStyle == null) {
                contentStyle = parentContentStyle;
            }
            excelContentProperty.setContentStyleProperty(StyleProperty.build(contentStyle));

            ContentFontStyle contentFontStyle = field.getAnnotation(ContentFontStyle.class);
            if (contentFontStyle == null) {
                contentFontStyle = parentContentFontStyle;
            }
            excelContentProperty.setContentFontProperty(FontProperty.build(contentFontStyle));

            excelContentProperty.setDateTimeFormatProperty(
                DateTimeFormatProperty.build(field.getAnnotation(DateTimeFormat.class)));
            excelContentProperty.setNumberFormatProperty(
                NumberFormatProperty.build(field.getAnnotation(NumberFormat.class)));

            fieldContentMap.put(field.getName(), excelContentProperty);
        }
        return fieldContentMap;
    }

    /**
     * Parsing field in the class
     *
     * @param clazz               Need to parse the class
     * @param configurationHolder configuration
     */
    public static FieldCache declaredFields(Class<?> clazz, ConfigurationHolder configurationHolder) {
        switch (configurationHolder.globalConfiguration().getFiledCacheLocation()) {
            case THREAD_LOCAL:
                Map<FieldCacheKey, FieldCache> fieldCacheMap = FIELD_THREAD_LOCAL.get();
                if (fieldCacheMap == null) {
                    fieldCacheMap = MapUtils.newHashMap();
                    FIELD_THREAD_LOCAL.set(fieldCacheMap);
                }
                return fieldCacheMap.computeIfAbsent(new FieldCacheKey(clazz, configurationHolder), key -> {
                    return doDeclaredFields(clazz, configurationHolder);
                });
            case MEMORY:
                return FIELD_CACHE.computeIfAbsent(new FieldCacheKey(clazz, configurationHolder), key -> {
                    return doDeclaredFields(clazz, configurationHolder);
                });
            case NONE:
                return doDeclaredFields(clazz, configurationHolder);
            default:
                throw new UnsupportedOperationException("unsupported enum");
        }
    }

    private static FieldCache doDeclaredFields(Class<?> clazz, ConfigurationHolder configurationHolder) {
        List<Field> tempFieldList = new ArrayList<>();
        Class<?> tempClass = clazz;
        // When the parent class is null, it indicates that the parent class (Object class) has reached the top
        // level.
        while (tempClass != null) {
            Collections.addAll(tempFieldList, tempClass.getDeclaredFields());
            // Get the parent class and give it to yourself
            tempClass = tempClass.getSuperclass();
        }
        // Screening of field
        Map<Integer, List<FieldWrapper>> orderFieldMap = new TreeMap<>();
        Map<Integer, FieldWrapper> indexFieldMap = new TreeMap<>();
        Set<String> ignoreSet = new HashSet<>();

        ExcelIgnoreUnannotated excelIgnoreUnannotated = clazz.getAnnotation(ExcelIgnoreUnannotated.class);
        for (Field field : tempFieldList) {
            declaredOneField(field, orderFieldMap, indexFieldMap, ignoreSet, excelIgnoreUnannotated);
        }
        Map<Integer, FieldWrapper> sortedFieldMap = buildSortedAllFieldMap(orderFieldMap, indexFieldMap);
        FieldCache fieldCache = new FieldCache(sortedFieldMap, indexFieldMap);

        if (!(configurationHolder instanceof WriteHolder)) {
            return fieldCache;
        }

        WriteHolder writeHolder = (WriteHolder)configurationHolder;

        boolean needIgnore = !CollectionUtils.isEmpty(writeHolder.excludeColumnFieldNames())
            || !CollectionUtils.isEmpty(writeHolder.excludeColumnIndexes())
            || !CollectionUtils.isEmpty(writeHolder.includeColumnFieldNames())
            || !CollectionUtils.isEmpty(writeHolder.includeColumnIndexes());

        if (!needIgnore) {
            return fieldCache;
        }
        // ignore filed
        Map<Integer, FieldWrapper> tempSortedFieldMap = MapUtils.newHashMap();
        int index = 0;
        for (Map.Entry<Integer, FieldWrapper> entry : sortedFieldMap.entrySet()) {
            Integer key = entry.getKey();
            FieldWrapper field = entry.getValue();

            // The current field needs to be ignored
            if (writeHolder.ignore(field.getFieldName(), entry.getKey())) {
                ignoreSet.add(field.getFieldName());
                indexFieldMap.remove(index);
            } else {
                // Mandatory sorted fields
                if (indexFieldMap.containsKey(key)) {
                    tempSortedFieldMap.put(key, field);
                } else {
                    // Need to reorder automatically
                    // Check whether the current key is already in use
                    while (tempSortedFieldMap.containsKey(index)) {
                        index++;
                    }
                    tempSortedFieldMap.put(index++, field);
                }
            }
        }
        fieldCache.setSortedFieldMap(tempSortedFieldMap);

        // resort field
        resortField(writeHolder, fieldCache);
        return fieldCache;
    }

    /**
     * it only works when {@link WriteHolder#includeColumnFieldNames()}  or
     * {@link WriteHolder#includeColumnIndexes()}  has value
     * and {@link WriteHolder#orderByIncludeColumn()}  is true
     **/
    private static void resortField(WriteHolder writeHolder, FieldCache fieldCache) {
        if (!writeHolder.orderByIncludeColumn()) {
            return;
        }
        Map<Integer, FieldWrapper> indexFieldMap = fieldCache.getIndexFieldMap();

        Collection<String> includeColumnFieldNames = writeHolder.includeColumnFieldNames();
        if (!CollectionUtils.isEmpty(includeColumnFieldNames)) {
            // Field sorted map
            Map<String, Integer> filedIndexMap = MapUtils.newHashMap();
            int fieldIndex = 0;
            for (String includeColumnFieldName : includeColumnFieldNames) {
                filedIndexMap.put(includeColumnFieldName, fieldIndex++);
            }

            // rebuild sortedFieldMap
            Map<Integer, FieldWrapper> tempSortedFieldMap = MapUtils.newHashMap();
            fieldCache.getSortedFieldMap().forEach((index, field) -> {
                Integer tempFieldIndex = filedIndexMap.get(field.getFieldName());
                if (tempFieldIndex != null) {
                    tempSortedFieldMap.put(tempFieldIndex, field);

                    //  The user has redefined the ordering and the ordering of annotations needs to be invalidated
                    if (!tempFieldIndex.equals(index)) {
                        indexFieldMap.remove(index);
                    }
                }
            });
            fieldCache.setSortedFieldMap(tempSortedFieldMap);
            return;
        }

        Collection<Integer> includeColumnIndexes = writeHolder.includeColumnIndexes();
        if (!CollectionUtils.isEmpty(includeColumnIndexes)) {
            // Index sorted map
            Map<Integer, Integer> filedIndexMap = MapUtils.newHashMap();
            int fieldIndex = 0;
            for (Integer includeColumnIndex : includeColumnIndexes) {
                filedIndexMap.put(includeColumnIndex, fieldIndex++);
            }

            // rebuild sortedFieldMap
            Map<Integer, FieldWrapper> tempSortedFieldMap = MapUtils.newHashMap();
            fieldCache.getSortedFieldMap().forEach((index, field) -> {
                Integer tempFieldIndex = filedIndexMap.get(index);

                //  The user has redefined the ordering and the ordering of annotations needs to be invalidated
                if (tempFieldIndex != null) {
                    tempSortedFieldMap.put(tempFieldIndex, field);
                }
            });
            fieldCache.setSortedFieldMap(tempSortedFieldMap);
        }
    }

    private static Map<Integer, FieldWrapper> buildSortedAllFieldMap(Map<Integer, List<FieldWrapper>> orderFieldMap,
        Map<Integer, FieldWrapper> indexFieldMap) {

        Map<Integer, FieldWrapper> sortedAllFieldMap = new HashMap<>(
            (orderFieldMap.size() + indexFieldMap.size()) * 4 / 3 + 1);

        Map<Integer, FieldWrapper> tempIndexFieldMap = new HashMap<>(indexFieldMap);
        int index = 0;
        for (List<FieldWrapper> fieldList : orderFieldMap.values()) {
            for (FieldWrapper field : fieldList) {
                while (tempIndexFieldMap.containsKey(index)) {
                    sortedAllFieldMap.put(index, tempIndexFieldMap.get(index));
                    tempIndexFieldMap.remove(index);
                    index++;
                }
                sortedAllFieldMap.put(index, field);
                index++;
            }
        }
        sortedAllFieldMap.putAll(tempIndexFieldMap);
        return sortedAllFieldMap;
    }

    private static void declaredOneField(Field field, Map<Integer, List<FieldWrapper>> orderFieldMap,
        Map<Integer, FieldWrapper> indexFieldMap, Set<String> ignoreSet,
        ExcelIgnoreUnannotated excelIgnoreUnannotated) {
        String fieldName = FieldUtils.resolveCglibFieldName(field);
        FieldWrapper fieldWrapper = new FieldWrapper();
        fieldWrapper.setField(field);
        fieldWrapper.setFieldName(fieldName);

        ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);

        if (excelIgnore != null) {
            ignoreSet.add(fieldName);
            return;
        }
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        boolean noExcelProperty = excelProperty == null && excelIgnoreUnannotated != null;
        if (noExcelProperty) {
            ignoreSet.add(fieldName);
            return;
        }
        boolean isStaticFinalOrTransient =
            (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
                || Modifier.isTransient(field.getModifiers());
        if (excelProperty == null && isStaticFinalOrTransient) {
            ignoreSet.add(fieldName);
            return;
        }
        // set heads
        if (excelProperty != null) {
            fieldWrapper.setHeads(excelProperty.value());
        }

        if (excelProperty != null && excelProperty.index() >= 0) {
            if (indexFieldMap.containsKey(excelProperty.index())) {
                throw new ExcelCommonException(
                    "The index of '" + indexFieldMap.get(excelProperty.index()).getFieldName()
                        + "' and '" + field.getName() + "' must be inconsistent");
            }
            indexFieldMap.put(excelProperty.index(), fieldWrapper);
            return;
        }

        int order = Integer.MAX_VALUE;
        if (excelProperty != null) {
            order = excelProperty.order();
        }
        List<FieldWrapper> orderFieldList = orderFieldMap.computeIfAbsent(order, key -> ListUtils.newArrayList());
        orderFieldList.add(fieldWrapper);
    }

    /**
     * <p>Gets a {@code List} of all interfaces implemented by the given
     * class and its superclasses.</p>
     *
     * <p>The order is determined by looking through each interface in turn as
     * declared in the source file and following its hierarchy up. Then each
     * superclass is considered in the same way. Later duplicates are ignored,
     * so the order is maintained.</p>
     *
     * @param cls the class to look up, may be {@code null}
     * @return the {@code List} of interfaces in order,
     * {@code null} if null input
     */
    public static List<Class<?>> getAllInterfaces(final Class<?> cls) {
        if (cls == null) {
            return null;
        }

        final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
        getAllInterfaces(cls, interfacesFound);

        return new ArrayList<>(interfacesFound);
    }

    /**
     * Gets the interfaces for the specified class.
     *
     * @param cls             the class to look up, may be {@code null}
     * @param interfacesFound the {@code Set} of interfaces for the class
     */
    private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
        while (cls != null) {
            final Class<?>[] interfaces = cls.getInterfaces();

            for (final Class<?> i : interfaces) {
                if (interfacesFound.add(i)) {
                    getAllInterfaces(i, interfacesFound);
                }
            }

            cls = cls.getSuperclass();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class ContentPropertyKey {
        private Class<?> clazz;
        private Class<?> headClass;
        private String fieldName;
    }

    @Data
    public static class FieldCacheKey {
        private Class<?> clazz;
        private Collection<String> excludeColumnFieldNames;
        private Collection<Integer> excludeColumnIndexes;
        private Collection<String> includeColumnFieldNames;
        private Collection<Integer> includeColumnIndexes;

        FieldCacheKey(Class<?> clazz, ConfigurationHolder configurationHolder) {
            this.clazz = clazz;
            if (configurationHolder instanceof WriteHolder) {
                WriteHolder writeHolder = (WriteHolder)configurationHolder;
                this.excludeColumnFieldNames = writeHolder.excludeColumnFieldNames();
                this.excludeColumnIndexes = writeHolder.excludeColumnIndexes();
                this.includeColumnFieldNames = writeHolder.includeColumnFieldNames();
                this.includeColumnIndexes = writeHolder.includeColumnIndexes();
            }
        }
    }

    public static void removeThreadLocalCache() {
        FIELD_THREAD_LOCAL.remove();
        CLASS_CONTENT_THREAD_LOCAL.remove();
        CONTENT_THREAD_LOCAL.remove();
    }
}

