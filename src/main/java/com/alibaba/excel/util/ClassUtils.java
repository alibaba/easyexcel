package com.alibaba.excel.util;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.write.metadata.holder.WriteHolder;

/**
 * Class utils
 *
 * @author Jiaju Zhuang
 **/
public class ClassUtils {

    public static final Map<Class<?>, SoftReference<FieldCache>> FIELD_CACHE = new ConcurrentHashMap<>();

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
        FieldCache fieldCache = getFieldCache(clazz);
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

    private static FieldCache getFieldCache(Class<?> clazz) {
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
            declaredFields(clazz);
        }
        return FIELD_CACHE.get(clazz).get();
    }

    private static void declaredFields(Class<?> clazz) {
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

}
