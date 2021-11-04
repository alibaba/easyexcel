package com.alibaba.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.metadata.property.DateTimeFormatProperty;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.metadata.property.FontProperty;
import com.alibaba.excel.metadata.property.NumberFormatProperty;
import com.alibaba.excel.metadata.property.StyleProperty;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.sf.cglib.beans.BeanMap;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Apache Software Foundation (ASF)
 */
public class ClassUtils {

    public static final Map<Class<?>, FieldCache> FIELD_CACHE = new ConcurrentHashMap<>();

    /**
     * The cache configuration information for each of the class
     */
    public static final Map<Class<?>, Map<String, ExcelContentProperty>> CLASS_CONTENT_CACHE
        = new ConcurrentHashMap<>();

    /**
     * The cache configuration information for each of the class
     */
    public static final Map<ContentPropertyKey, ExcelContentProperty> CONTENT_CACHE = new ConcurrentHashMap<>();

    /**
     * Calculate the configuration information for the class
     *
     * @param dataMap
     * @param headClazz
     * @param fieldName
     * @return
     */
    public static ExcelContentProperty declaredExcelContentProperty(Map<?, ?> dataMap, Class<?> headClazz,
        String fieldName) {
        Class<?> clazz = null;
        if (dataMap instanceof BeanMap) {
            Object bean = ((BeanMap)dataMap).getBean();
            if (bean != null) {
                clazz = bean.getClass();
            }
        }
        return getExcelContentProperty(clazz, headClazz, fieldName);
    }

    private static ExcelContentProperty getExcelContentProperty(Class<?> clazz, Class<?> headClass, String fieldName) {
        return CONTENT_CACHE.computeIfAbsent(buildKey(clazz, headClass, fieldName), key -> {
            ExcelContentProperty excelContentProperty = Optional.ofNullable(declaredFieldContentMap(clazz))
                .map(map -> map.get(fieldName))
                .orElse(null);
            ExcelContentProperty headExcelContentProperty = Optional.ofNullable(declaredFieldContentMap(headClass))
                .map(map -> map.get(fieldName))
                .orElse(null);
            ExcelContentProperty combineExcelContentProperty = new ExcelContentProperty();

            combineExcelContentProperty(combineExcelContentProperty, headExcelContentProperty);
            if (clazz != headClass) {
                combineExcelContentProperty(combineExcelContentProperty, excelContentProperty);
            }
            return combineExcelContentProperty;
        });

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

    private static Map<String, ExcelContentProperty> declaredFieldContentMap(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return CLASS_CONTENT_CACHE.computeIfAbsent(clazz, key -> {
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
                            Converter<?> converter = convertClazz.newInstance();
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
        });
    }

    /**
     * Parsing filed in the class
     *
     * @param clazz             Need to parse the class
     * @param sortedAllFiledMap Complete the map of sorts
     * @param indexFiledMap     Use the index to sort fields
     * @param ignoreMap         You want to ignore field map
     * @param needIgnore        If you want to ignore fields need to ignore
     * @param holder            holder
     */
    public static void declaredFields(Class<?> clazz, Map<Integer, Field> sortedAllFiledMap,
        Map<Integer, Field> indexFiledMap, Map<String, Field> ignoreMap, Boolean needIgnore, Holder holder) {
        FieldCache fieldCache = declaredFields(clazz);
        if (fieldCache == null) {
            return;
        }
        if (ignoreMap != null) {
            ignoreMap.putAll(fieldCache.getIgnoreMap());
        }
        Map<Integer, Field> tempIndexFieldMap = indexFiledMap;
        if (tempIndexFieldMap == null) {
            tempIndexFieldMap = MapUtils.newTreeMap();
        }
        tempIndexFieldMap.putAll(fieldCache.getIndexFiledMap());

        Map<Integer, Field> originSortedAllFiledMap = fieldCache.getSortedAllFiledMap();
        if (!needIgnore) {
            sortedAllFiledMap.putAll(originSortedAllFiledMap);
            return;
        }

        int index = 0;
        for (Map.Entry<Integer, Field> entry : originSortedAllFiledMap.entrySet()) {
            Integer key = entry.getKey();
            Field field = entry.getValue();

            // The current field needs to be ignored
            if (((WriteHolder)holder).ignore(entry.getValue().getName(), entry.getKey())) {
                if (ignoreMap != null) {
                    ignoreMap.put(field.getName(), field);
                }
                tempIndexFieldMap.remove(index);
            } else {
                // Mandatory sorted fields
                if (tempIndexFieldMap.containsKey(key)) {
                    sortedAllFiledMap.put(key, field);
                } else {
                    // Need to reorder automatically
                    // Check whether the current key is already in use
                    while (sortedAllFiledMap.containsKey(index)) {
                        index++;
                    }
                    sortedAllFiledMap.put(index++, field);
                }
            }
        }
    }

    public static void declaredFields(Class<?> clazz, Map<Integer, Field> sortedAllFiledMap, Boolean needIgnore,
        WriteHolder writeHolder) {
        declaredFields(clazz, sortedAllFiledMap, null, null, needIgnore, writeHolder);
    }

    private static FieldCache declaredFields(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return FIELD_CACHE.computeIfAbsent(clazz, key -> {
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
            Map<Integer, List<Field>> orderFiledMap = new TreeMap<Integer, List<Field>>();
            Map<Integer, Field> indexFiledMap = new TreeMap<Integer, Field>();
            Map<String, Field> ignoreMap = new HashMap<String, Field>(16);

            ExcelIgnoreUnannotated excelIgnoreUnannotated = clazz.getAnnotation(ExcelIgnoreUnannotated.class);
            for (Field field : tempFieldList) {
                declaredOneField(field, orderFiledMap, indexFiledMap, ignoreMap, excelIgnoreUnannotated);
            }
            return new FieldCache(buildSortedAllFiledMap(orderFiledMap, indexFiledMap), indexFiledMap, ignoreMap);
        });
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
        Map<Integer, Field> indexFiledMap, Map<String, Field> ignoreMap,
        ExcelIgnoreUnannotated excelIgnoreUnannotated) {

        ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
        if (excelIgnore != null) {
            ignoreMap.put(field.getName(), field);
            return;
        }
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        boolean noExcelProperty = excelProperty == null && excelIgnoreUnannotated != null;
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
        List<Field> orderFiledList = orderFiledMap.computeIfAbsent(order, key -> ListUtils.newArrayList());
        orderFiledList.add(field);
    }

    private static class FieldCache {

        private final Map<Integer, Field> sortedAllFiledMap;
        private final Map<Integer, Field> indexFiledMap;
        private final Map<String, Field> ignoreMap;

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
}
