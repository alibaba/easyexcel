package com.alibaba.excel.metadata.property;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.AutoConverter;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.Holder;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.metadata.holder.AbstractWriteHolder;

/**
 * Define the header attribute of excel
 *
 * @author jipengfei
 */
public class ExcelHeadProperty {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHeadProperty.class);
    /**
     * Custom class
     */
    private Class headClazz;
    /**
     * The types of head
     */
    private HeadKindEnum headKind;
    /**
     * The number of rows in the line with the most rows
     */
    private int headRowNumber;
    /**
     * Configuration header information
     */
    private Map<Integer, Head> headMap;
    /**
     * Configuration column information
     */
    private Map<Integer, ExcelContentProperty> contentPropertyMap;
    /**
     * Configuration column information
     */
    private Map<String, ExcelContentProperty> fieldNameContentPropertyMap;
    /**
     * Fields ignored
     */
    private Map<String, Field> ignoreMap;

    public ExcelHeadProperty(Holder holder, Class headClazz, List<List<String>> head, Boolean convertAllField) {
        this.headClazz = headClazz;
        headMap = new TreeMap<Integer, Head>();
        contentPropertyMap = new TreeMap<Integer, ExcelContentProperty>();
        fieldNameContentPropertyMap = new HashMap<String, ExcelContentProperty>();
        ignoreMap = new HashMap<String, Field>(16);
        headKind = HeadKindEnum.NONE;
        headRowNumber = 0;
        if (head != null && !head.isEmpty()) {
            int headIndex = 0;
            for (int i = 0; i < head.size(); i++) {
                if (holder instanceof AbstractWriteHolder) {
                    if (((AbstractWriteHolder)holder).ignore(null, i)) {
                        continue;
                    }
                }
                headMap.put(headIndex, new Head(headIndex, null, head.get(i), Boolean.FALSE, Boolean.TRUE));
                contentPropertyMap.put(headIndex, null);
                headIndex++;
            }
            headKind = HeadKindEnum.STRING;
        }
        // convert headClazz to head
        initColumnProperties(holder, convertAllField);

        initHeadRowNumber();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("The initialization sheet/table 'ExcelHeadProperty' is complete , head kind is {}", headKind);
        }
    }

    private void initHeadRowNumber() {
        headRowNumber = 0;
        for (Head head : headMap.values()) {
            List<String> list = head.getHeadNameList();
            if (list != null && list.size() > headRowNumber) {
                headRowNumber = list.size();
            }
        }
        for (Head head : headMap.values()) {
            List<String> list = head.getHeadNameList();
            if (list != null && !list.isEmpty() && list.size() < headRowNumber) {
                int lack = headRowNumber - list.size();
                int last = list.size() - 1;
                for (int i = 0; i < lack; i++) {
                    list.add(list.get(last));
                }
            }
        }
    }

    private void initColumnProperties(Holder holder, Boolean convertAllField) {
        if (headClazz == null) {
            return;
        }
        // Declared fields
        List<Field> defaultFieldList = new ArrayList<Field>();
        Map<Integer, Field> customFieldMap = new TreeMap<Integer, Field>();
        ClassUtils.declaredFields(headClazz, defaultFieldList, customFieldMap, ignoreMap, convertAllField);

        List<Map.Entry<Field, Boolean>> exportFieldBoolPairsList = new ArrayList<Map.Entry<Field, Boolean>>();
        int index = 0;
        while (customFieldMap.containsKey(index)) {
            Field field = customFieldMap.get(index);
            Map.Entry<Field, Boolean> fieldBooleanPair =
                new AbstractMap.SimpleEntry<Field, Boolean>(field, Boolean.TRUE);
            exportFieldBoolPairsList.add(fieldBooleanPair);
            index++;
        }
        for (Field field : defaultFieldList) {
            Map.Entry<Field, Boolean> fieldBoolPair = new AbstractMap.SimpleEntry<Field, Boolean>(field, Boolean.FALSE);
            exportFieldBoolPairsList.add(fieldBoolPair);
        }

        sortExportColumnFields(holder, exportFieldBoolPairsList);
        initColumnProperties(holder, exportFieldBoolPairsList);

        for (Map.Entry<Integer, Field> entry : customFieldMap.entrySet()) {
            initOneColumnProperty(holder, entry.getKey(), entry.getValue(), Boolean.TRUE);
        }
        headKind = HeadKindEnum.CLASS;
    }

    /**
     * Give the field and flag pair list and arrange them in the specified order according to the user's settings. The
     * field is what the user want to export to excel, the flag indicates whether the order of the field in excel is
     * specified.
     *
     * @param holder
     *            Write holder which keeps the parameters of a sheet.
     * @param exportFieldBoolPairList
     *            Keep all the fields and the flag(which indicate whether the field order is specified) of the head
     *            class except the ignored. It will be modified after this function is called.
     */
    private void sortExportColumnFields(Holder holder, List<Map.Entry<Field, Boolean>> exportFieldBoolPairList) {
        if (holder instanceof AbstractWriteHolder) {
            Collection<String> includeColumnFieldNames = ((AbstractWriteHolder)holder).getIncludeColumnFieldNames();
            if (includeColumnFieldNames != null) {
                Map<String, Map.Entry<Field, Boolean>> exportFieldMap =
                    new TreeMap<String, Map.Entry<Field, Boolean>>();
                List<String> includeColumnFieldNameList = new ArrayList<String>(includeColumnFieldNames);
                for (Map.Entry<Field, Boolean> fieldBoolPair : exportFieldBoolPairList) {
                    if (includeColumnFieldNameList.contains(fieldBoolPair.getKey().getName())) {
                        exportFieldMap.put(fieldBoolPair.getKey().getName(), fieldBoolPair);
                    }
                }
                exportFieldBoolPairList.clear();
                for (String fieldName : includeColumnFieldNameList) {
                    exportFieldBoolPairList.add(exportFieldMap.get(fieldName));
                }
                return;
            }

            Collection<Integer> includeColumnIndexes = ((AbstractWriteHolder)holder).getIncludeColumnIndexes();
            if (includeColumnIndexes != null) {
                List<Map.Entry<Field, Boolean>> tempFieldsList = new ArrayList<Map.Entry<Field, Boolean>>();
                for (Integer includeColumnIndex : includeColumnIndexes) {
                    tempFieldsList.add(exportFieldBoolPairList.get(includeColumnIndex));
                }
                exportFieldBoolPairList.clear();
                exportFieldBoolPairList.addAll(tempFieldsList);
                return;
            }

            int index = 0;
            for (Map.Entry<Field, Boolean> fieldBoolPair : exportFieldBoolPairList) {
                if (((AbstractWriteHolder)holder).ignore(fieldBoolPair.getKey().getName(), index)) {
                    exportFieldBoolPairList.remove(fieldBoolPair);
                }
                index++;
            }
        }
    }

    /**
     * Initialize column properties.
     *
     * @param holder
     *            Write holder which keeps the parameters of a sheet.
     * @param exportFieldBoolPairList
     *            Keep the fields which will be exported to excel and the flag which indicate whether the field order in
     *            excel is specified.
     */
    private void initColumnProperties(Holder holder, List<Map.Entry<Field, Boolean>> exportFieldBoolPairList) {
        int index = 0;
        for (Map.Entry<Field, Boolean> fieldBoolPair : exportFieldBoolPairList) {
            initOneColumnProperty(holder, index, fieldBoolPair.getKey(), fieldBoolPair.getValue());
            index++;
        }
    }

    /**
     * Initialization column property
     *
     * @param holder
     * @param index
     * @param field
     * @param forceIndex
     * @return Ignore current field
     */
    private boolean initOneColumnProperty(Holder holder, int index, Field field, Boolean forceIndex) {
        if (holder instanceof AbstractWriteHolder) {
            if (((AbstractWriteHolder)holder).ignore(field.getName(), index)) {
                return true;
            }
        }
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        List<String> tmpHeadList = new ArrayList<String>();
        boolean notForceName = excelProperty == null || excelProperty.value().length <= 0
            || (excelProperty.value().length == 1 && StringUtils.isEmpty((excelProperty.value())[0]));
        if (headMap.containsKey(index)) {
            tmpHeadList.addAll(headMap.get(index).getHeadNameList());
        } else {
            if (notForceName) {
                tmpHeadList.add(field.getName());
            } else {
                Collections.addAll(tmpHeadList, excelProperty.value());
            }
        }
        Head head = new Head(index, field.getName(), tmpHeadList, forceIndex, !notForceName);
        ExcelContentProperty excelContentProperty = new ExcelContentProperty();
        if (excelProperty != null) {
            Class<? extends Converter> convertClazz = excelProperty.converter();
            if (convertClazz != AutoConverter.class) {
                try {
                    Converter converter = convertClazz.newInstance();
                    excelContentProperty.setConverter(converter);
                } catch (Exception e) {
                    throw new ExcelCommonException("Can not instance custom converter:" + convertClazz.getName());
                }
            }
        }
        excelContentProperty.setHead(head);
        excelContentProperty.setField(field);
        excelContentProperty
            .setDateTimeFormatProperty(DateTimeFormatProperty.build(field.getAnnotation(DateTimeFormat.class)));
        excelContentProperty
            .setNumberFormatProperty(NumberFormatProperty.build(field.getAnnotation(NumberFormat.class)));
        headMap.put(index, head);
        contentPropertyMap.put(index, excelContentProperty);
        fieldNameContentPropertyMap.put(field.getName(), excelContentProperty);
        return false;
    }

    public Class getHeadClazz() {
        return headClazz;
    }

    public void setHeadClazz(Class headClazz) {
        this.headClazz = headClazz;
    }

    public HeadKindEnum getHeadKind() {
        return headKind;
    }

    public void setHeadKind(HeadKindEnum headKind) {
        this.headKind = headKind;
    }

    public boolean hasHead() {
        return headKind != HeadKindEnum.NONE;
    }

    public int getHeadRowNumber() {
        return headRowNumber;
    }

    public void setHeadRowNumber(int headRowNumber) {
        this.headRowNumber = headRowNumber;
    }

    public Map<Integer, Head> getHeadMap() {
        return headMap;
    }

    public void setHeadMap(Map<Integer, Head> headMap) {
        this.headMap = headMap;
    }

    public Map<Integer, ExcelContentProperty> getContentPropertyMap() {
        return contentPropertyMap;
    }

    public void setContentPropertyMap(Map<Integer, ExcelContentProperty> contentPropertyMap) {
        this.contentPropertyMap = contentPropertyMap;
    }

    public Map<String, ExcelContentProperty> getFieldNameContentPropertyMap() {
        return fieldNameContentPropertyMap;
    }

    public void setFieldNameContentPropertyMap(Map<String, ExcelContentProperty> fieldNameContentPropertyMap) {
        this.fieldNameContentPropertyMap = fieldNameContentPropertyMap;
    }

    public Map<String, Field> getIgnoreMap() {
        return ignoreMap;
    }

    public void setIgnoreMap(Map<String, Field> ignoreMap) {
        this.ignoreMap = ignoreMap;
    }
}
